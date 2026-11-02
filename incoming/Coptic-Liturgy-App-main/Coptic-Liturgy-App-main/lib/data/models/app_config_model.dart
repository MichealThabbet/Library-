class AppConfigModel {
  final AppInfoModel appInfo;
  final NavigationModel navigation;
  final Map<String, String> contentFiles;
  final Map<String, ThemeModel> themes;

  AppConfigModel({
    required this.appInfo,
    required this.navigation,
    required this.contentFiles,
    required this.themes,
  });

  factory AppConfigModel.fromJson(Map<String, dynamic> json) {
    return AppConfigModel(
      appInfo: AppInfoModel.fromJson(json['app_info'] ?? {}),
      navigation: NavigationModel.fromJson(json['navigation'] ?? {}),
      contentFiles: Map<String, String>.from(json['content_files'] ?? {}),
      themes: (json['themes'] as Map<String, dynamic>? ?? {}).map(
        (key, value) => MapEntry(key, ThemeModel.fromJson(value)),
      ),
    );
  }
}

class AppInfoModel {
  final String name;
  final String version;
  final String defaultLanguage;

  AppInfoModel({
    required this.name,
    required this.version,
    required this.defaultLanguage,
  });

  factory AppInfoModel.fromJson(Map<String, dynamic> json) {
    return AppInfoModel(
      name: json['name'] ?? '',
      version: json['version'] ?? '',
      defaultLanguage: json['default_language'] ?? 'ar',
    );
  }
}

class NavigationModel {
  final String mainMenu;
  final String uiComponents;

  NavigationModel({required this.mainMenu, required this.uiComponents});

  factory NavigationModel.fromJson(Map<String, dynamic> json) {
    return NavigationModel(
      mainMenu: json['main_menu'] ?? '',
      uiComponents: json['ui_components'] ?? '',
    );
  }
}

class ThemeModel {
  final String primaryColor;
  final String backgroundColor;
  final String textColor;

  ThemeModel({
    required this.primaryColor,
    required this.backgroundColor,
    required this.textColor,
  });

  factory ThemeModel.fromJson(Map<String, dynamic> json) {
    return ThemeModel(
      primaryColor: json['primary_color'] ?? '',
      backgroundColor: json['background_color'] ?? '',
      textColor: json['text_color'] ?? '',
    );
  }
}
