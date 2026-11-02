import 'package:coptic/core/theme/app_theme.dart';
import 'package:coptic/core/utils/app_router.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'core/utils/app_state_manager.dart';
import 'core/utils/dependency_injection.dart';
import 'presentation/pages/home_page.dart';
import 'core/services/cache_manager.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  // Initialize services
  await CacheManager().preloadEssentialContent();

  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => AppStateManager(),
      child: Consumer<AppStateManager>(
        builder: (context, appState, child) {
          return MaterialApp(
            title: 'المكتبة الطقسية المتقدمة',
            theme: AppTheme.lightTheme,
            darkTheme: AppTheme.darkTheme,
            themeMode: appState.isDarkMode ? ThemeMode.dark : ThemeMode.light,
            home: const HomePage(),
            onGenerateRoute: AppRouter.generateRoute,
            debugShowCheckedModeBanner: false,
          );
        },
      ),
    );
  }
}
