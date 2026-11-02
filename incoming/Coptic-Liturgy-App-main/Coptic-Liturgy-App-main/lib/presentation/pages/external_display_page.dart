import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';
import 'package:flutter_displaymode/flutter_displaymode.dart';

import '../../core/utils/app_state_manager.dart';
import '../../domain/entities/content_entity.dart';

class ExternalDisplayPage extends StatefulWidget {
  final List<ContentEntity>? content;
  final String? title;
  final int initialSlide;

  const ExternalDisplayPage({
    super.key,
    this.content,
    this.title,
    this.initialSlide = 0,
  });

  @override
  State<ExternalDisplayPage> createState() => _ExternalDisplayPageState();
}

class _ExternalDisplayPageState extends State<ExternalDisplayPage> {
  late PageController _pageController;
  int _currentSlide = 0;
  bool _isExternalDisplayAvailable = false;

  @override
  void initState() {
    super.initState();
    _currentSlide = widget.initialSlide;
    _pageController = PageController(initialPage: widget.initialSlide);
    _initializeDisplay();
  }

  Future<void> _initializeDisplay() async {
    try {
      // Check for external display capabilities
      final modes = await FlutterDisplayMode.supported;
      print('عدد أوضاع العرض المتاحة: ${modes.length}');

      if (modes.isNotEmpty) {
        _isExternalDisplayAvailable = true;
        print('تم العثور على أوضاع عرض متاحة');
      } else {
        print('لا توجد أوضاع عرض إضافية - سيتم العرض على الشاشة الأساسية');
      }
    } catch (e) {
      print('خطأ في مكتبة العرض: $e');
      print('سيتم العرض على الشاشة الأساسية فقط');
    }
  }

  RouterDelegate _createPresentationRouter() {
    return _PresentationRouterDelegate(
      content: widget.content,
      title: widget.title,
      pageController: _pageController,
      currentSlide: _currentSlide,
      onSlideChanged: (index) {
        setState(() {
          _currentSlide = index;
        });
      },
    );
  }

  void _showNoExternalDisplayDialog() {
    showDialog(
      context: context,
      builder:
          (context) => AlertDialog(
            title: const Text('لا توجد شاشة خارجية'),
            content: const Text('يرجى توصيل شاشة خارجية لبدء العرض'),
            actions: [
              TextButton(
                onPressed: () {
                  Navigator.pop(context);
                  Navigator.pop(context);
                },
                child: const Text('موافق'),
              ),
            ],
          ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('التحكم في العرض'),
        actions: [
          IconButton(
            onPressed: _goToFirst,
            icon: const Icon(Icons.first_page),
            tooltip: 'البداية',
          ),
          IconButton(
            onPressed: _previousSlide,
            icon: const Icon(Icons.skip_previous),
            tooltip: 'الشريحة السابقة',
          ),
          IconButton(
            onPressed: _nextSlide,
            icon: const Icon(Icons.skip_next),
            tooltip: 'الشريحة التالية',
          ),
          IconButton(
            onPressed: _goToLast,
            icon: const Icon(Icons.last_page),
            tooltip: 'النهاية',
          ),
          IconButton(
            onPressed: _showSettings,
            icon: const Icon(Icons.settings),
            tooltip: 'الإعدادات',
          ),
          IconButton(
            onPressed: _stopPresentation,
            icon: const Icon(Icons.stop),
            tooltip: 'إيقاف العرض',
          ),
        ],
      ),
      body: Column(
        children: [
          // شريط معلومات العرض
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
            color: Theme.of(context).primaryColor.withOpacity(0.1),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  widget.title ?? 'عرض تقديمي',
                  style: Theme.of(context).textTheme.titleMedium,
                ),
                if (widget.content != null && widget.content!.isNotEmpty)
                  Text(
                    'الشريحة ${_currentSlide + 1} من ${widget.content!.length}',
                    style: Theme.of(context).textTheme.bodyMedium,
                  ),
              ],
            ),
          ),
          // مساحة العرض الرئيسية
          Expanded(
            child: Container(
              width: double.infinity,
              color: Colors.black,
              child: _buildPreview(),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildPreview() {
    if (widget.content == null || widget.content!.isEmpty) {
      return const Center(
        child: Text(
          'لا يوجد محتوى للعرض',
          style: TextStyle(color: Colors.white, fontSize: 24),
        ),
      );
    }

    final content = widget.content![_currentSlide];
    return Container(
      padding: const EdgeInsets.all(32),
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
                  style: const TextStyle(
                    fontSize: 32,
                    height: 1.8,
                    color: Colors.orange,
                    fontWeight: FontWeight.w500,
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
                  style: const TextStyle(
                    fontSize: 24,
                    height: 1.6,
                    color: Colors.lightBlue,
                    fontStyle: FontStyle.italic,
                  ),
                ),
              ),
            ),
        ],
      ),
    );
  }

  void _nextSlide() {
    if (widget.content != null && _currentSlide < widget.content!.length - 1) {
      setState(() {
        _currentSlide++;
      });
      if (_pageController.hasClients) {
        _pageController.nextPage(
          duration: const Duration(milliseconds: 300),
          curve: Curves.easeInOut,
        );
      }
    }
  }

  void _previousSlide() {
    if (_currentSlide > 0) {
      setState(() {
        _currentSlide--;
      });
      if (_pageController.hasClients) {
        _pageController.previousPage(
          duration: const Duration(milliseconds: 300),
          curve: Curves.easeInOut,
        );
      }
    }
  }

  void _goToFirst() {
    setState(() {
      _currentSlide = 0;
    });
    if (_pageController.hasClients) {
      _pageController.animateToPage(
        0,
        duration: const Duration(milliseconds: 300),
        curve: Curves.easeInOut,
      );
    }
  }

  void _goToLast() {
    if (widget.content != null) {
      setState(() {
        _currentSlide = widget.content!.length - 1;
      });
      if (_pageController.hasClients) {
        _pageController.animateToPage(
          widget.content!.length - 1,
          duration: const Duration(milliseconds: 300),
          curve: Curves.easeInOut,
        );
      }
    }
  }

  void _stopPresentation() {
    Navigator.pop(context);
  }

  void _showSettings() {
    // إعدادات العرض
  }

  @override
  void dispose() {
    _pageController.dispose();
    super.dispose();
  }
}

class _PresentationRouterDelegate extends RouterDelegate with ChangeNotifier {
  final List<ContentEntity>? content;
  final String? title;
  final PageController pageController;
  final int currentSlide;
  final Function(int) onSlideChanged;

  _PresentationRouterDelegate({
    required this.content,
    required this.title,
    required this.pageController,
    required this.currentSlide,
    required this.onSlideChanged,
  });

  @override
  Future<bool> popRoute() async {
    return false;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<AppStateManager>(
      builder: (context, appState, child) {
        return Scaffold(
          backgroundColor:
              appState.isDarkMode ? Colors.black : const Color(0xFFF8F4E3),
          body:
              content != null && content!.isNotEmpty
                  ? _buildContentView(appState)
                  : _buildDefaultView(appState),
        );
      },
    );
  }

  Widget _buildContentView(AppStateManager appState) {
    return PageView.builder(
      controller: pageController,
      onPageChanged: onSlideChanged,
      itemCount: content!.length,
      itemBuilder: (context, index) {
        final contentItem = content![index];
        return Container(
          padding: const EdgeInsets.all(32.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              if (contentItem.arabicText.isNotEmpty)
                Expanded(
                  child: Center(
                    child: Text(
                      contentItem.arabicText,
                      textAlign: TextAlign.center,
                      textDirection: TextDirection.rtl,
                      style: TextStyle(
                        fontSize: appState.fontSize * 2.5,
                        height: 1.8,
                        color:
                            appState.isDarkMode
                                ? Colors.orange[200]
                                : Colors.brown[900],
                        fontWeight: FontWeight.w500,
                      ),
                    ),
                  ),
                ),
              if (contentItem.copticText?.isNotEmpty == true)
                Expanded(
                  child: Center(
                    child: Text(
                      contentItem.copticText!,
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        fontSize: appState.fontSize * 2.0,
                        height: 1.6,
                        color:
                            appState.isDarkMode
                                ? Colors.blue[200]
                                : Colors.indigo[800],
                        fontStyle: FontStyle.italic,
                      ),
                    ),
                  ),
                ),
            ],
          ),
        );
      },
    );
  }

  Widget _buildDefaultView(AppStateManager appState) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            width: 300,
            height: 300,
            decoration: BoxDecoration(
              color: appState.isDarkMode ? Colors.grey[800] : Colors.grey[300],
              borderRadius: BorderRadius.circular(150),
              border: Border.all(
                color:
                    appState.isDarkMode
                        ? Colors.orange[200]!
                        : Colors.brown[900]!,
                width: 4,
              ),
            ),
            child: Icon(
              Icons.church,
              size: 150,
              color:
                  appState.isDarkMode ? Colors.orange[200] : Colors.brown[900],
            ),
          ),
          const SizedBox(height: 60),
          Text(
            'كنيسة السيدة العذراء مريم ببهناى',
            textAlign: TextAlign.center,
            textDirection: TextDirection.rtl,
            style: TextStyle(
              fontSize: appState.fontSize * 3.0,
              height: 1.8,
              color:
                  appState.isDarkMode ? Colors.orange[200] : Colors.brown[900],
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }

  @override
  Future<void> setNewRoutePath(configuration) async {}
}
