import 'package:coptic/data/datasources/content_local_data_source.dart';
import 'package:coptic/data/services/config_service.dart';
import 'package:coptic/presentation/widgets/common/responsive_text.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../core/utils/app_state_manager.dart';

import '../../data/models/menu_model.dart';
import '../../data/models/content_model.dart';

class VirginMaryPraisesPage extends StatefulWidget {
  const VirginMaryPraisesPage({super.key});

  @override
  State<VirginMaryPraisesPage> createState() => _VirginMaryPraisesPageState();
}

class _VirginMaryPraisesPageState extends State<VirginMaryPraisesPage> {
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();
  String _searchQuery = '';
  MenuItemModel? _selectedItem;
  List<MenuItemModel> _filteredItems = [];
  List<MenuItemModel> _allItems = [];
  List<ContentModel> _currentContent = [];
  bool _isLoadingContent = false;

  @override
  void initState() {
    super.initState();
    _loadMenu();
  }

  Future<void> _loadMenu() async {
    try {
      final menu = await ConfigService.instance.loadMenu(
        'virgin_mary_praises_menu.json',
      );
      setState(() {
        _allItems = menu.items;
        _filteredItems = menu.items;
      });
    } catch (e) {
      // Handle error
    }
  }

  void _filterItems(String query) {
    setState(() {
      _searchQuery = query;
      _filteredItems =
          _allItems
              .where(
                (item) =>
                    item.title.toLowerCase().contains(query.toLowerCase()),
              )
              .toList();
    });
  }

  void _loadContent(String jsonFile) async {
    setState(() {
      _isLoadingContent = true;
    });

    try {
      final dataSource = ContentLocalDataSourceImpl();
      final content = await dataSource.getContentFromJson(jsonFile);
      setState(() {
        _currentContent = content;
        _isLoadingContent = false;
      });
    } catch (e) {
      setState(() {
        _isLoadingContent = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      appBar: AppBar(
        title: const Text('تماجيد العذراء'),
        leading: IconButton(
          icon: const Icon(Icons.menu),
          onPressed: () => _scaffoldKey.currentState?.openDrawer(),
        ),
      ),
      drawer: _buildDrawer(),
      body: _buildContent(),
    );
  }

  Widget _buildDrawer() {
    return Drawer(
      child: Column(
        children: [
          const DrawerHeader(
            decoration: BoxDecoration(color: Colors.blue),
            child: Text(
              'تماجيد العذراء',
              style: TextStyle(color: Colors.white, fontSize: 24),
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextField(
              decoration: const InputDecoration(
                hintText: 'بحث...',
                prefixIcon: Icon(Icons.search),
                border: OutlineInputBorder(),
              ),
              onChanged: _filterItems,
            ),
          ),
          Expanded(
            child: ListView.builder(
              itemCount: _filteredItems.length,
              itemBuilder: (context, index) {
                final item = _filteredItems[index];
                return ListTile(
                  title: Text(item.title),
                  selected: _selectedItem?.id == item.id,
                  onTap: () {
                    setState(() {
                      _selectedItem = item;
                    });
                    if (item.target?.isNotEmpty == true) {
                      _loadContent(item.target!);
                    }
                    Navigator.pop(context); // Close drawer
                  },
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildContent() {
    if (_selectedItem == null) {
      return const Center(
        child: Text(
          'اختر تمجيد من القائمة الجانبية',
          style: TextStyle(fontSize: 18),
        ),
      );
    }

    if (_isLoadingContent) {
      return const Center(child: CircularProgressIndicator());
    }

    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            _selectedItem!.title,
            style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 20),
          Expanded(
            child: ListView.builder(
              itemCount: _currentContent.length,
              itemBuilder: (context, index) {
                final content = _currentContent[index];
                return Padding(
                  padding: const EdgeInsets.symmetric(vertical: 8.0),
                  child: ResponsiveText(
                    content.arabicText,
                    textAlign: TextAlign.center,
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
