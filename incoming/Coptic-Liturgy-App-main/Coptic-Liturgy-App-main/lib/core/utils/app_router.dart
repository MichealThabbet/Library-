import 'package:flutter/material.dart';
import '../../presentation/pages/home_page.dart';
import '../../presentation/pages/liturgies_page.dart';
import '../../presentation/pages/settings_page.dart';
import '../../presentation/pages/content_page.dart';
import '../../presentation/pages/search_page.dart';
import '../../presentation/pages/nahda_page.dart';

class AppRouter {
  static const String home = '/';
  static const String liturgies = '/liturgies';
  static const String settings = '/settings';
  static const String content = '/content';
  static const String search = '/search';
  static const String nahda = '/nahda';

  static Route<dynamic> generateRoute(RouteSettings settings) {
    switch (settings.name) {
      case home:
        return MaterialPageRoute(builder: (_) => const HomePage());
      case liturgies:
        return MaterialPageRoute(builder: (_) => const LiturgiesPage());
      case AppRouter.settings:
        return MaterialPageRoute(
          builder:
              (_) => Scaffold(
                appBar: AppBar(title: const Text('الإعدادات')),
                body: const SettingsPageContent(),
              ),
        );
      case nahda:
        return MaterialPageRoute(builder: (_) => const NahdaPage());
      case search:
        return MaterialPageRoute(builder: (_) => const SearchPage());
      case content:
        final args = settings.arguments as Map<String, dynamic>;
        return MaterialPageRoute(
          builder:
              (_) =>
                  ContentPage(title: args['title'], jsonFile: args['jsonFile']),
        );
      default:
        return MaterialPageRoute(
          builder:
              (_) => Scaffold(
                body: Center(
                  child: Text('No route defined for ${settings.name}'),
                ),
              ),
        );
    }
  }
}
