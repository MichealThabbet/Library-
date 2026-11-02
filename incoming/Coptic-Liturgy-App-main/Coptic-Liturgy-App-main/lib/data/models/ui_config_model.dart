class UiConfigModel {
  final String title;
  final List<ActionButtonModel> actions;
  final DisplaySettingsModel displaySettings;

  UiConfigModel({
    required this.title,
    required this.actions,
    required this.displaySettings,
  });

  factory UiConfigModel.fromJson(Map<String, dynamic> json) {
    return UiConfigModel(
      title: json['title'] ?? '',
      actions: (json['actions'] as List?)
          ?.map((action) => ActionButtonModel.fromJson(action))
          .toList() ?? [],
      displaySettings: DisplaySettingsModel.fromJson(json['display_settings'] ?? {}),
    );
  }
}

class ActionButtonModel {
  final String id;
  final String icon;
  final String tooltip;
  final int order;

  ActionButtonModel({
    required this.id,
    required this.icon,
    required this.tooltip,
    required this.order,
  });

  factory ActionButtonModel.fromJson(Map<String, dynamic> json) {
    return ActionButtonModel(
      id: json['id'] ?? '',
      icon: json['icon'] ?? '',
      tooltip: json['tooltip'] ?? '',
      order: json['order'] ?? 0,
    );
  }
}

class DisplaySettingsModel {
  final String backgroundColor;
  final String arabicTextColor;
  final String copticTextColor;
  final double arabicFontSize;
  final double copticFontSize;
  final double padding;

  DisplaySettingsModel({
    required this.backgroundColor,
    required this.arabicTextColor,
    required this.copticTextColor,
    required this.arabicFontSize,
    required this.copticFontSize,
    required this.padding,
  });

  factory DisplaySettingsModel.fromJson(Map<String, dynamic> json) {
    return DisplaySettingsModel(
      backgroundColor: json['background_color'] ?? '#000000',
      arabicTextColor: json['arabic_text_color'] ?? '#FFA726',
      copticTextColor: json['coptic_text_color'] ?? '#81D4FA',
      arabicFontSize: (json['arabic_font_size'] ?? 32).toDouble(),
      copticFontSize: (json['coptic_font_size'] ?? 24).toDouble(),
      padding: (json['padding'] ?? 32).toDouble(),
    );
  }
}