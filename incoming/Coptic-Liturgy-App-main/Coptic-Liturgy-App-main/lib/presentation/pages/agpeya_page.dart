import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../core/utils/app_state_manager.dart';
import '../../core/utils/constants.dart';
import '../widgets/content_tile_widget.dart';
import '../../domain/entities/menu_entity.dart';
import 'content_page.dart';

class AgpeyaPage extends StatelessWidget {
  const AgpeyaPage({super.key});

  @override
  Widget build(BuildContext context) {
    final appState = Provider.of<AppStateManager>(context);

    final prayers = [
      MenuEntity(
        id: 'agpeya_baker',
        title: 'صلاة باكر',
        iconName: 'schedule',
        category: 'agpeya',
        jsonFile: 'agpeya_baker.json',
      ),
      MenuEntity(
        id: 'agpeya_third',
        title: 'صلاة الساعة الثالثة',
        iconName: 'schedule',
        category: 'agpeya',
        jsonFile: 'agpeya_third.json',
      ),
      MenuEntity(
        id: 'agpeya_sixth',
        title: 'صلاة الساعة السادسة',
        iconName: 'schedule',
        category: 'agpeya',
        jsonFile: 'agpeya_sixth.json',
      ),
      MenuEntity(
        id: 'agpeya_ninth',
        title: 'صلاة الساعة التاسعة',
        iconName: 'schedule',
        category: 'agpeya',
        jsonFile: 'agpeya_ninth.json',
      ),
      MenuEntity(
        id: 'agpeya_sunset',
        title: 'صلاة الغروب',
        iconName: 'schedule',
        category: 'agpeya',
        jsonFile: 'agpeya_sunset.json',
      ),
      MenuEntity(
        id: 'agpeya_sleep',
        title: 'صلاة النوم',
        iconName: 'schedule',
        category: 'agpeya',
        jsonFile: 'agpeya_sleep.json',
      ),
      MenuEntity(
        id: 'agpeya_midnight',
        title: 'صلاة نصف الليل',
        iconName: 'schedule',
        category: 'agpeya',
        jsonFile: 'agpeya_midnight.json',
      ),
    ];

    return Scaffold(
      appBar: AppBar(
        title: const Text('الأجبية'),
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
          itemCount: prayers.length,
          itemBuilder: (context, index) {
            final prayer = prayers[index];
            return ContentTileWidget(
              menuItem: prayer,
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => ContentPage(
                      title: prayer.title,
                      jsonFile: prayer.jsonFile!,
                    ),
                  ),
                );
              },
            );
          },
        ),
      ),
    );
  }
}

