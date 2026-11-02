import 'package:coptic/core/utils/app_state_manager.dart';
import 'package:coptic/core/utils/constants.dart';
import 'package:coptic/presentation/widgets/common/responsive_text.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class SlideHeaderWidget extends StatelessWidget {
  final int slideNumber;
  final bool isExpanded;

  const SlideHeaderWidget({
    super.key,
    required this.slideNumber,
    required this.isExpanded,
  });

  @override
  Widget build(BuildContext context) {
    final appState = Provider.of<AppStateManager>(context);
    
    return Row(
      mainAxisAlignment: MainAxisAlignment.end,
      children: [
        Icon(
          isExpanded ? Icons.expand_less : Icons.expand_more,
          size: appState.fontSize,
          color: Theme.of(context).iconTheme.color,
        ),
        const SizedBox(width: 8),
        ResponsiveText(
          'شريحة $slideNumber',
          style: TextStyle(
            fontSize: appState.fontSize - AppConstants.fontSizeStep,
            fontWeight: FontWeight.bold,
          ),
        ),
      ],
    );
  }
}