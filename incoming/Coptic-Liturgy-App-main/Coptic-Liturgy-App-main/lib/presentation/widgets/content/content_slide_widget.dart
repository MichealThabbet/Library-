import 'package:coptic/domain/entities/content_entity.dart';
import 'package:coptic/presentation/widgets/common/app_card.dart';
import 'package:coptic/presentation/widgets/content/slide_header_widget.dart';
import 'package:coptic/presentation/widgets/content/text_section_widget.dart';
import 'package:flutter/material.dart';

class ContentSlideWidget extends StatefulWidget {
  final ContentEntity content;
  final VoidCallback? onTap;
  final bool showSlideNumber;

  const ContentSlideWidget({
    super.key,
    required this.content,
    this.onTap,
    this.showSlideNumber = true,
  });

  @override
  State<ContentSlideWidget> createState() => _ContentSlideWidgetState();
}

class _ContentSlideWidgetState extends State<ContentSlideWidget> {
  bool _isExpanded = true;

  @override
  Widget build(BuildContext context) {
    return AppCard(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          if (widget.showSlideNumber) _buildSlideHeader(),
          if (_isExpanded) _buildContentBody(),
        ],
      ),
    );
  }

  Widget _buildSlideHeader() {
    return GestureDetector(
      onTap: () => setState(() => _isExpanded = !_isExpanded),
      child: SlideHeaderWidget(
        slideNumber: widget.content.order,
        isExpanded: _isExpanded,
      ),
    );
  }

  Widget _buildContentBody() {
    return Padding(
      padding: const EdgeInsets.only(top: 16),
      child: IntrinsicHeight(
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            if (widget.content.copticText?.isNotEmpty == true)
              Expanded(
                child: TextSectionWidget(
                  text: widget.content.copticText!,
                  textType: TextType.coptic,
                ),
              ),
            if (_shouldShowDivider()) _buildVerticalDivider(),
            if (widget.content.arabicText.isNotEmpty)
              Expanded(
                child: TextSectionWidget(
                  text: widget.content.arabicText,
                  textType: TextType.arabic,
                ),
              ),
          ],
        ),
      ),
    );
  }

  bool _shouldShowDivider() {
    return widget.content.copticText?.isNotEmpty == true &&
           widget.content.arabicText.isNotEmpty;
  }

  Widget _buildVerticalDivider() {
    return Container(
      width: 1,
      margin: const EdgeInsets.symmetric(horizontal: 16),
      color: Theme.of(context).dividerColor,
    );
  }
}





