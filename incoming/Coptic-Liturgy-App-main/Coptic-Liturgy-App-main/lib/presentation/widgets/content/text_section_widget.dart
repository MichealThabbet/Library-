import 'package:coptic/core/utils/app_state_manager.dart';
import 'package:coptic/presentation/widgets/common/responsive_text.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

enum TextType { arabic, coptic }

class TextSectionWidget extends StatelessWidget {
  final String text;
  final TextType textType;

  const TextSectionWidget({
    super.key,
    required this.text,
    required this.textType,
  });

  @override
  Widget build(BuildContext context) {
    final appState = Provider.of<AppStateManager>(context);
    
    return ResponsiveText(
      text,
      textAlign: TextAlign.center,
      textDirection: textType == TextType.arabic ? TextDirection.rtl : TextDirection.ltr,
      selectable: true,
      style: _getTextStyle(appState),
    );
  }

  TextStyle _getTextStyle(AppStateManager appState) {
    switch (textType) {
      case TextType.arabic:
        return TextStyle(
          fontSize: appState.fontSize,
          height: 1.5,
          fontWeight: FontWeight.w500,
          color: appState.isDarkMode ? Colors.orange[200] : Colors.brown[900],
        );
      case TextType.coptic:
        return TextStyle(
          fontSize: appState.fontSize,
          height: 1.5,
          color: appState.isDarkMode ? Colors.blue[200] : Colors.indigo[800],
        );
    }
  }
}