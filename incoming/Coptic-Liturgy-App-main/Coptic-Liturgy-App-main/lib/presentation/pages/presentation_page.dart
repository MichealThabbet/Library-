import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';
import '../../core/utils/app_state_manager.dart';
import '../../domain/entities/content_entity.dart';

class PresentationPage extends StatefulWidget {
  final List<ContentEntity> content;
  final String title;
  final int initialSlide;

  const PresentationPage({
    super.key,
    required this.content,
    required this.title,
    this.initialSlide = 0,
  });

  @override
  State<PresentationPage> createState() => _PresentationPageState();
}

class _PresentationPageState extends State<PresentationPage> {
  late PageController _pageController;
  int _currentSlide = 0;
  late FocusNode _focusNode;

  @override
  void initState() {
    super.initState();
    _currentSlide = widget.initialSlide;
    _pageController = PageController(initialPage: widget.initialSlide);
    _focusNode = FocusNode();
  }

  @override
  void dispose() {
    _pageController.dispose();
    _focusNode.dispose();
    super.dispose();
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

  void _handleKeyEvent(RawKeyEvent event) {
    if (event is RawKeyDownEvent) {
      if (event.logicalKey == LogicalKeyboardKey.arrowRight ||
          event.logicalKey == LogicalKeyboardKey.space) {
        _nextSlide();
      } else if (event.logicalKey == LogicalKeyboardKey.arrowLeft) {
        _previousSlide();
      } else if (event.logicalKey == LogicalKeyboardKey.escape) {
        Navigator.pop(context);
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final appState = Provider.of<AppStateManager>(context);

    return Scaffold(
      backgroundColor: appState.isDarkMode ? Colors.black : Colors.white,
      appBar: AppBar(
        title: Text(widget.title),
        actions: [
          Text('${_currentSlide + 1} / ${widget.content.length}'),
          const SizedBox(width: 16),
        ],
      ),
      body: RawKeyboardListener(
        focusNode: _focusNode,
        onKey: _handleKeyEvent,
        autofocus: true,
        child: GestureDetector(
          onTap: _nextSlide,
          onHorizontalDragEnd: (details) {
            if (details.primaryVelocity! > 0) {
              _previousSlide();
            } else if (details.primaryVelocity! < 0) {
              _nextSlide();
            }
          },
          child: PageView.builder(
            controller: _pageController,
            onPageChanged: (index) {
              setState(() {
                _currentSlide = index;
              });
            },
            itemCount: widget.content.length,
            itemBuilder: (context, index) {
              final content = widget.content[index];
              return Padding(
                padding: const EdgeInsets.all(32.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    if (content.arabicText.isNotEmpty)
                      Expanded(
                        child: Center(
                          child: Text(
                            content.arabicText,
                            textAlign: TextAlign.center,
                            textDirection: TextDirection.rtl,
                            style: TextStyle(
                              fontSize: appState.fontSize * 1.5,
                              height: 1.8,
                              color: appState.isDarkMode 
                                  ? Colors.orange[200] 
                                  : Colors.brown[900],
                            ),
                          ),
                        ),
                      ),
                    if (content.copticText?.isNotEmpty == true)
                      Expanded(
                        child: Center(
                          child: Text(
                            content.copticText!,
                            textAlign: TextAlign.center,
                            style: TextStyle(
                              fontSize: appState.fontSize * 1.2,
                              height: 1.6,
                              color: appState.isDarkMode 
                                  ? Colors.blue[200] 
                                  : Colors.indigo[800],
                            ),
                          ),
                        ),
                      ),
                  ],
                ),
              );
            },
          ),
        ),
      ),
    );
  }
}
