import 'package:flutter/material.dart';

class GestureNavigationWrapper extends StatelessWidget {
  final Widget child;
  final VoidCallback? onSwipeLeft;
  final VoidCallback? onSwipeRight;

  const GestureNavigationWrapper({
    super.key,
    required this.child,
    this.onSwipeLeft,
    this.onSwipeRight,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onHorizontalDragEnd: _handleHorizontalDrag,
      child: child,
    );
  }

  void _handleHorizontalDrag(DragEndDetails details) {
    if (details.primaryVelocity! > 0) {
      onSwipeRight?.call();
    } else if (details.primaryVelocity! < 0) {
      onSwipeLeft?.call();
    }
  }
}