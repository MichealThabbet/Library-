import 'package:coptic/domain/entities/content_entity.dart';
import 'package:coptic/presentation/widgets/content/content_slide_widget.dart';
import 'package:coptic/presentation/widgets/navigation/gesture_navigation_wrapper.dart';
import 'package:coptic/presentation/widgets/navigation/keyboard_navigation_wrapper.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../../core/utils/app_state_manager.dart';

class ContentListView extends StatefulWidget {
  final List<ContentEntity> contentList;
  final String currentFile;
  final Function(int)? onSlideSelected;

  const ContentListView({
    super.key,
    required this.contentList,
    required this.currentFile,
    this.onSlideSelected,
  });

  @override
  State<ContentListView> createState() => _ContentListViewState();
}

class _ContentListViewState extends State<ContentListView> {
  late final FocusNode _focusNode;
  late final ScrollController _scrollController;

  @override
  void initState() {
    super.initState();
    _focusNode = FocusNode();
    _scrollController = ScrollController();
  }

  @override
  Widget build(BuildContext context) {
    return KeyboardNavigationWrapper(
      focusNode: _focusNode,
      onNavigateToNext: _navigateToNextFile,
      onNavigateToPrevious: _navigateToPreviousFile,
      child: GestureNavigationWrapper(
        onSwipeLeft: _navigateToNextFile,
        onSwipeRight: _navigateToPreviousFile,
        child: ListView.separated(
          controller: _scrollController,
          itemCount: widget.contentList.length,
          separatorBuilder: (context, index) => const SizedBox(height: 8),
          itemBuilder: (context, index) {
            return Column(
              children: [
                ContentSlideWidget(
                  content: widget.contentList[index],
                  onTap: () => widget.onSlideSelected?.call(index),
                ),
                // Add action buttons
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 16),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      TextButton.icon(
                        onPressed: () => widget.onSlideSelected?.call(index),
                        icon: const Icon(Icons.slideshow),
                        label: const Text('عرض'),
                      ),
                      TextButton.icon(
                        onPressed: () => _viewDetails(index),
                        icon: const Icon(Icons.info),
                        label: const Text('تفاصيل'),
                      ),
                    ],
                  ),
                ),
              ],
            );
          },
        ),
      ),
    );
  }

  void _navigateToNextFile() {
    // Implementation for file navigation
  }

  void _navigateToPreviousFile() {
    // Implementation for file navigation
  }

  void _viewDetails(int index) {
    // Implementation for viewing slide details
    // You can show a dialog, navigate to a details page, etc.
    showDialog(
      context: context,
      builder:
          (context) => AlertDialog(
            title: Text(
              'تفاصيل الشريحة ${widget.contentList[index].order}',
            ),
            content: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                if (widget.contentList[index].arabicText.isNotEmpty)
                  Text('النص العربي:\n${widget.contentList[index].arabicText}'),
                if (widget
                    .contentList[index]
                    .copticText
                    ?.isNotEmpty == true)
                  Text(
                    'النص القبطي:\n${widget.contentList[index].copticText}',
                  ),
              ],
            ),
            actions: [
              TextButton(
                onPressed: () => Navigator.of(context).pop(),
                child: const Text('إغلاق'),
              ),
            ],
          ),
    );
  }

  @override
  void dispose() {
    _focusNode.dispose();
    _scrollController.dispose();
    super.dispose();
  }
}

Widget _buildContentItem(BuildContext context, ContentEntity content, int index) {
  final appState = Provider.of<AppStateManager>(context);
  
  return Container(
    margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
    decoration: BoxDecoration(
      borderRadius: BorderRadius.circular(12),
      image: content.backgroundImage != null
          ? DecorationImage(
              image: AssetImage(content.backgroundImage!),
              fit: BoxFit.cover,
              opacity: 0.15,
            )
          : null,
      gradient: content.backgroundImage == null
          ? LinearGradient(
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
              colors: [
                Colors.blue.withValues(alpha: 0.1),
                Colors.blue.withValues(alpha: 0.05),
              ],
            )
          : null,
    ),
    child: Card(
      elevation: 4,
      color: Colors.white.withOpacity(0.9),
      child: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            if (content.image != null) ...[
              ClipRRect(
                borderRadius: BorderRadius.circular(8),
                child: Image.asset(
                  content.image!,
                  height: 120,
                  fit: BoxFit.contain,
                  errorBuilder: (context, error, stackTrace) {
                    return Container(
                      height: 120,
                      decoration: BoxDecoration(
                        color: Colors.grey[200],
                        borderRadius: BorderRadius.circular(8),
                      ),
                      child: Icon(Icons.image, size: 40, color: Colors.grey[400]),
                    );
                  },
                ),
              ),
              const SizedBox(height: 16),
            ],
            Text(
              content.arabicText,
              style: TextStyle(
                fontSize: appState.fontSize + 2,
                height: 1.8,
                fontWeight: FontWeight.w600,
                color: Colors.black87,
              ),
              textAlign: TextAlign.center,
            ),
            if (content.copticText != null && content.copticText!.isNotEmpty) ...[
              const SizedBox(height: 12),
              Container(
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: Colors.amber[50],
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: Colors.amber[200]!),
                ),
                child: Text(
                  content.copticText!,
                  style: TextStyle(
                    fontSize: appState.fontSize,
                    height: 1.6,
                    fontStyle: FontStyle.italic,
                    color: Colors.brown[700],
                  ),
                  textAlign: TextAlign.center,
                ),
              ),
            ],
          ],
        ),
      ),
    ),
  );
}








