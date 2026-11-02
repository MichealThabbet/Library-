import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../core/utils/app_state_manager.dart';
import '../../data/datasources/menu_local_datasource.dart';
import '../../data/repositories/menu_repository_impl.dart';
import '../../domain/usecases/get_menu_items_usecase.dart';
import '../widgets/content_tile_widget.dart';
import 'content_page.dart';
import 'praises_page.dart';

class SubCategoryPage extends StatelessWidget {
  final String title;
  final String category;

  const SubCategoryPage({
    super.key,
    required this.title,
    required this.category,
  });

  @override
  Widget build(BuildContext context) {
    final menuDataSource = MenuLocalDataSourceImpl();
    final menuRepository = MenuRepositoryImpl(menuDataSource);
    final getMenuItemsUseCase = GetMenuItemsUseCase(menuRepository);
    final appState = Provider.of<AppStateManager>(context);

    final items = getMenuItemsUseCase.execute(category);

    return Scaffold(
      appBar: AppBar(
        title: Text(title),
        actions: [
          IconButton(
            icon: Icon(
              appState.isDarkMode ? Icons.light_mode : Icons.dark_mode,
            ),
            onPressed: () => appState.toggleDarkMode(),
          ),
        ],
      ),
      body: Padding(
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
                if (menuItem.category == 'virgin_mary_praises') {
                  // Navigate to special praises page
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder:
                          (context) => PraisesPage(
                            title: menuItem.title,
                          ),
                    ),
                  );
                } else if (menuItem.category == 'virgin_mary') {
                  // Navigate to virgin mary subcategory
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder:
                          (context) => SubCategoryPage(
                            title: menuItem.title,
                            category: menuItem.category,
                          ),
                    ),
                  );
                } else if (menuItem.jsonFile != null) {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder:
                          (context) => ContentPage(
                            title: menuItem.title,
                            jsonFile: menuItem.jsonFile!,
                          ),
                    ),
                  );
                }
              },
            );
          },
        ),
      ),
    );
  }
}

