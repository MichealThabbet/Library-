import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../core/utils/app_state_manager.dart';
import '../../domain/entities/menu_entity.dart';

class ContentTileWidget extends StatelessWidget {
  final MenuEntity menuItem;
  final VoidCallback onTap;

  const ContentTileWidget({
    super.key,
    required this.menuItem,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    final appState = Provider.of<AppStateManager>(context);

    return Card(
      elevation: 4,
      child: InkWell(
        onTap: onTap,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              _getIconData(menuItem.iconName),
              size: 48,
              color: Theme.of(context).primaryColor,
            ),
            const SizedBox(height: 16),
            Text(
              menuItem.title,
              style: TextStyle(
                fontSize: appState.fontSize,
                fontWeight: FontWeight.bold,
              ),
              textAlign: TextAlign.center,
            ),
            if (menuItem.description != null) ...[
              const SizedBox(height: 8),
              Text(
                menuItem.description!,
                style: TextStyle(
                  fontSize: appState.fontSize * 0.8,
                  color: Colors.grey[600],
                ),
                textAlign: TextAlign.center,
              ),
            ],
          ],
        ),
      ),
    );
  }

  IconData _getIconData(String iconName) {
    switch (iconName) {
      case 'church':
        return Icons.church;
      case 'music_note':
        return Icons.music_note;
      case 'book':
        return Icons.book;
      case 'schedule':
        return Icons.schedule;
      default:
        return Icons.help;
    }
  }
}
