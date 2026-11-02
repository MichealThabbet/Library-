import 'package:flutter/material.dart';
import '../../data/services/config_service.dart';
import '../../data/models/menu_model.dart';
import '../../domain/entities/menu_entity.dart';
import '../widgets/content_tile_widget.dart';

class MenuPage extends StatelessWidget {
  final String title;
  final String menuFile;

  const MenuPage({
    super.key,
    required this.title,
    required this.menuFile,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: FutureBuilder<MenuModel>(
        future: ConfigService.instance.loadMenu(menuFile),
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
                ],
              ),
            );
          } else if (!snapshot.hasData || snapshot.data!.items.isEmpty) {
            return const Center(child: Text('لا يوجد محتوى'));
          }

          final menu = snapshot.data!;
          return ListView.builder(
            padding: const EdgeInsets.all(16),
            itemCount: menu.items.length,
            itemBuilder: (context, index) {
              final item = menu.items[index];
              return ContentTileWidget(
                onTap: () {
                  // TODO: Implement navigation logic
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
      ),
    );
  }
}


