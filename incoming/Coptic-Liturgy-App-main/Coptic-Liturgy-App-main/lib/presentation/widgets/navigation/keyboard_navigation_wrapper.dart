import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class KeyboardNavigationWrapper extends StatelessWidget {
  final Widget child;
  final FocusNode focusNode;
  final VoidCallback? onNavigateToNext;
  final VoidCallback? onNavigateToPrevious;

  const KeyboardNavigationWrapper({
    super.key,
    required this.child,
    required this.focusNode,
    this.onNavigateToNext,
    this.onNavigateToPrevious,
  });

  @override
  Widget build(BuildContext context) {
    return RawKeyboardListener(
      focusNode: focusNode,
      onKey: _handleKeyEvent,
      autofocus: true,
      child: child,
    );
  }

  void _handleKeyEvent(RawKeyEvent event) {
    if (event is RawKeyDownEvent) {
      switch (event.logicalKey) {
        case LogicalKeyboardKey.arrowLeft:
          onNavigateToPrevious?.call();
          break;
        case LogicalKeyboardKey.arrowRight:
          onNavigateToNext?.call();
          break;
      }
    }
  }
}