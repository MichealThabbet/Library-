import 'package:coptic/core/utils/app_state_manager.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ResponsiveText extends StatelessWidget {
  final String text;
  final TextStyle? style;
  final TextAlign? textAlign;
  final TextDirection? textDirection;
  final int? maxLines;
  final bool selectable;

  const ResponsiveText(
    this.text, {
    super.key,
    this.style,
    this.textAlign,
    this.textDirection,
    this.maxLines,
    this.selectable = false,
  });

  @override
  Widget build(BuildContext context) {
    final appState = Provider.of<AppStateManager>(context);
    final theme = Theme.of(context);

    final baseStyle = style ?? theme.textTheme.bodyMedium!;
    final safeFontSize = (style?.fontSize ?? appState.fontSize ?? 16.0).clamp(
      12.0,
      32.0,
    );

    final effectiveStyle = baseStyle.copyWith(
      fontSize: safeFontSize,
      color:
          style?.color ?? (appState.isDarkMode ? Colors.white : Colors.black),
    );

    return selectable
        ? SelectableText(
          text,
          style: effectiveStyle,
          textAlign: textAlign,
          textDirection: textDirection,
        )
        : Text(
          text,
          style: effectiveStyle,
          textAlign: textAlign,
          textDirection: textDirection,
          maxLines: maxLines,
          overflow: maxLines != null ? TextOverflow.ellipsis : null,
        );
  }
}
