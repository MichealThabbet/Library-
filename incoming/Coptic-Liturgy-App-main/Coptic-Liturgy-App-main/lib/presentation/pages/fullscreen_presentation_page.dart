import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';
import '../../core/utils/app_state_manager.dart';
import '../../domain/entities/content_entity.dart';

class FullScreenPresentationPage extends StatefulWidget {
  final List<ContentEntity> content;
  final String title;
  final int initialSlide;

  const FullScreenPresentationPage({
    super.key,
    required this.content,
    required this.title,
    this.initialSlide = 0,
  });

  @override
  State<FullScreenPresentationPage> createState() => _FullScreenPresentationPageState();
}

class _FullScreenPresentationPageState extends State<FullScreenPresentationPage> {
  late PageController _pageController;
  int _currentSlide = 0;

  @override
  void initState() {
    super.initState();
    _currentSlide = widget.initialSlide;
    _pageController = PageController(initialPage: widget.initialSlide);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      body: Stack(
        children: [
          PageView.builder(
            controller: _pageController,
            itemCount: widget.content.length,
            onPageChanged: (index) {
              setState(() {
                _currentSlide = index;
              });
            },
            itemBuilder: (context, index) {
              final content = widget.content[index];
              return _buildSlide(content);
            },
          ),
          // Navigation controls
          Positioned(
            bottom: 50,
            left: 0,
            right: 0,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                IconButton(
                  onPressed: _currentSlide > 0 ? _previousSlide : null,
                  icon: const Icon(Icons.arrow_back_ios, color: Colors.white, size: 30),
                ),
                Text(
                  '${_currentSlide + 1} / ${widget.content.length}',
                  style: const TextStyle(color: Colors.white, fontSize: 18),
                ),
                IconButton(
                  onPressed: _currentSlide < widget.content.length - 1 ? _nextSlide : null,
                  icon: const Icon(Icons.arrow_forward_ios, color: Colors.white, size: 30),
                ),
              ],
            ),
          ),
          // Exit button
          Positioned(
            top: 50,
            right: 20,
            child: IconButton(
              onPressed: () => Navigator.pop(context),
              icon: const Icon(Icons.close, color: Colors.white, size: 30),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildSlide(ContentEntity content) {
    return Container(
      decoration: BoxDecoration(
        image: content.backgroundImage != null
            ? DecorationImage(
                image: AssetImage(content.backgroundImage!),
                fit: BoxFit.cover,
                onError: (error, stackTrace) {
                  // Use default background if image fails
                },
              )
            : null,
        gradient: content.backgroundImage == null
            ? const LinearGradient(
                begin: Alignment.topCenter,
                end: Alignment.bottomCenter,
                colors: [Color(0xFF1a1a2e), Color(0xFF16213e)],
              )
            : null,
      ),
      child: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(40),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              if (content.image != null) ...[
                Container(
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(15),
                    boxShadow: [
                      BoxShadow(
                        color: Colors.black.withOpacity(0.3),
                        blurRadius: 10,
                        offset: const Offset(0, 5),
                      ),
                    ],
                  ),
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(15),
                    child: Image.asset(
                      content.image!,
                      height: 200,
                      fit: BoxFit.contain,
                      errorBuilder: (context, error, stackTrace) {
                        return const SizedBox.shrink();
                      },
                    ),
                  ),
                ),
                const SizedBox(height: 40),
              ],
              // Arabic text
              if (content.arabicText.isNotEmpty) ...[
                Text(
                  content.arabicText,
                  textAlign: TextAlign.center,
                  textDirection: TextDirection.rtl,
                  style: const TextStyle(
                    fontSize: 32,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                    height: 1.8,
                    shadows: [
                      Shadow(
                        offset: Offset(2, 2),
                        blurRadius: 4,
                        color: Colors.black54,
                      ),
                    ],
                  ),
                ),
                const SizedBox(height: 30),
              ],
              // Coptic text
              if (content.copticText != null && content.copticText!.isNotEmpty) ...[
                Text(
                  content.copticText!,
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    fontSize: 24,
                    color: Colors.white70,
                    height: 1.6,
                    shadows: [
                      Shadow(
                        offset: Offset(1, 1),
                        blurRadius: 3,
                        color: Colors.black54,
                      ),
                    ],
                  ),
                ),
              ],
            ],
          ),
        ),
      ),
    );
  }

  void _nextSlide() {
    if (_currentSlide < widget.content.length - 1) {
      _pageController.nextPage(
        duration: const Duration(milliseconds: 300),
        curve: Curves.easeInOut,
      );
    }
  }

  void _previousSlide() {
    if (_currentSlide > 0) {
      _pageController.previousPage(
        duration: const Duration(milliseconds: 300),
        curve: Curves.easeInOut,
      );
    }
  }

  @override
  void dispose() {
    _pageController.dispose();
    super.dispose();
  }
}


