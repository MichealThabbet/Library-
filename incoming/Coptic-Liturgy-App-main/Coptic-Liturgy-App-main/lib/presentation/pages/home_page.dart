import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../core/utils/app_state_manager.dart';
import '../../core/utils/constants.dart';
import '../../core/utils/app_router.dart';
import '../../data/services/config_service.dart';
import '../../data/models/menu_model.dart';
import '../../data/datasources/menu_local_datasource.dart';
import '../../data/repositories/menu_repository_impl.dart';
import '../../domain/entities/menu_entity.dart';
import '../../domain/usecases/get_menu_items_usecase.dart';
import '../widgets/content_tile_widget.dart';
import '../widgets/adaptive_navigation.dart';
import 'fullscreen_presentation_page.dart';
import 'menu_page.dart';
import 'content_page.dart';
import 'praises_page.dart';
import 'settings_page.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _selectedIndex = 0;
  final PageController _pageController = PageController();

  @override
  void dispose() {
    _pageController.dispose();
    super.dispose();
  }

  void _onDestinationSelected(int index) {
    setState(() {
      _selectedIndex = index;
    });
    _pageController.animateToPage(
      index,
      duration: const Duration(milliseconds: 300),
      curve: Curves.easeInOut,
    );
  }

  @override
  Widget build(BuildContext context) {
    final appState = Provider.of<AppStateManager>(context);

    return AdaptiveNavigation(
      selectedIndex: _selectedIndex,
      onDestinationSelected: _onDestinationSelected,
      child: Scaffold(
        appBar: AppBar(
          title: const Text('المكتبة الطقسية'),
          actions: [
            IconButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder:
                        (context) => const FullScreenPresentationPage(
                          content: [],
                          title: 'عرض تقديمي',
                        ),
                  ),
                );
              },
              icon: const Icon(Icons.slideshow),
              tooltip: 'بدء العرض',
            ),
            IconButton(
              icon: Icon(
                appState.isDarkMode ? Icons.light_mode : Icons.dark_mode,
              ),
              onPressed: () => appState.toggleDarkMode(),
            ),
            IconButton(
              icon: const Icon(Icons.text_increase),
              onPressed: () => appState.increaseFontSize(),
            ),
            IconButton(
              icon: const Icon(Icons.text_decrease),
              onPressed: () => appState.decreaseFontSize(),
            ),
          ],
        ),
        body: PageView(
          controller: _pageController,
          onPageChanged: (index) {
            setState(() {
              _selectedIndex = index;
            });
          },
          children: [
            _buildMainContent(),
            const MenuPage(title: 'القداسات', menuFile: 'liturgies_menu.json'),
            const MenuPage(title: 'التسبحة', menuFile: 'psalmody_menu.json'),
            const MenuPage(title: 'القراءات', menuFile: 'readings_menu.json'),
            const MenuPage(title: 'الأجبية', menuFile: 'agpeya_menu.json'),
            const SettingsPageContent(),
          ],
        ),
      ),
    );
  }

  Widget _buildMainContent() {
    return FutureBuilder<MenuModel>(
      future: ConfigService.instance.loadMenu('main_menu.json'),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Center(child: CircularProgressIndicator());
        } else if (snapshot.hasError) {
          return Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Icon(Icons.error, size: 64, color: Colors.red),
                const SizedBox(height: 16),
                Text('حدث خطأ: ${snapshot.error}'),
                const SizedBox(height: 16),
                ElevatedButton(
                  onPressed: () => setState(() {}),
                  child: const Text('إعادة المحاولة'),
                ),
              ],
            ),
          );
        } else if (!snapshot.hasData || snapshot.data!.items.isEmpty) {
          return const Center(child: Text('لا يوجد محتوى'));
        }

        final menu = snapshot.data!;
        // إضافة الأعياد والمناسبات
        final allItems = [
          ...menu.items,
          MenuItemModel(
            id: 'occasions',
            title: 'الأعياد والمناسبات',
            icon: 'celebration',
            category: 'occasions',
            target: 'occasions_menu.json',
          ),
          MenuItemModel(
            id: 'nahda',
            title: 'نهضة العذراء',
            icon: 'church',
            category: 'nahda',
            target: '',
          ),
        ];

        return GridView.builder(
          padding: const EdgeInsets.all(16),
          gridDelegate: const SliverGridDelegateWithMaxCrossAxisExtent(
            maxCrossAxisExtent: 200,
            mainAxisSpacing: 16,
            crossAxisSpacing: 16,
            childAspectRatio: 0.8,
          ),
          itemCount: allItems.length,
          itemBuilder: (context, index) {
            final item = allItems[index];
            return ContentTileWidget(
              onTap: () {
                if (item.id == 'occasions') {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => MenuPage(
                        title: item.title,
                        menuFile: item.target ?? '',
                      ),
                    ),
                  );
                } else if (item.id == 'nahda') {
                  Navigator.pushNamed(context, AppRouter.nahda);
                } else {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => ContentPage(
                        title: item.title,
                        jsonFile: item.target ?? '',
                      ),
                    ),
                  );
                }
              },
              menuItem: MenuEntity(
                id: item.id,
                title: item.title,
                iconName: item.icon ?? 'menu_book',
                category: item.category ?? '',
                jsonFile: item.target ?? '',
              ),
            );
          },
        );
      },
    );
  }

  Widget _getSelectedPage() {
    switch (_selectedIndex) {
      case 0:
        return _buildMainContent();
      case 1:
        return _buildLiturgiesPage();
      case 2:
        return _buildPsalmodyPage();
      case 3:
        return _buildReadingsPage();
      case 4:
        return _buildAgpeyaPage();
      case 5:
        return const SettingsPageContent();
      default:
        return _buildMainContent();
    }
  }

  Widget _buildLiturgiesPage() {
    final menuDataSource = MenuLocalDataSourceImpl();
    final menuRepository = MenuRepositoryImpl(menuDataSource);
    final getMenuItemsUseCase = GetMenuItemsUseCase(menuRepository);
    final items = getMenuItemsUseCase.execute(AppConstants.liturgiesCategory);

    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: GridView.builder(
        gridDelegate: const SliverGridDelegateWithMaxCrossAxisExtent(
          maxCrossAxisExtent: 250,
          mainAxisSpacing: 16,
          crossAxisSpacing: 16,
          childAspectRatio: 0.75,
        ),
        itemCount: items.length,
        itemBuilder: (context, index) {
          final menuItem = items[index];
          return ContentTileWidget(
            menuItem: menuItem,
            onTap: () {
              Navigator.pushNamed(
                context,
                AppRouter.content,
                arguments: {
                  'title': menuItem.title,
                  'jsonFile': menuItem.jsonFile,
                },
              );
            },
          );
        },
      ),
    );
  }

  Widget _buildPsalmodyPage() {
    // Similar implementation for psalmody
    return const Center(child: Text('التسبحة'));
  }

  Widget _buildReadingsPage() {
    // Similar implementation for readings
    return const Center(child: Text('القراءات'));
  }

  Widget _buildAgpeyaPage() {
    // Similar implementation for agpeya
    return const Center(child: Text('الأجبية'));
  }
}



