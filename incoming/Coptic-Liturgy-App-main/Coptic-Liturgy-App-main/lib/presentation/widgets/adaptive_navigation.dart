import 'package:flutter/material.dart';

class AdaptiveNavigation extends StatelessWidget {
  final int selectedIndex;
  final Function(int) onDestinationSelected;
  final Widget child;

  const AdaptiveNavigation({
    super.key,
    required this.selectedIndex,
    required this.onDestinationSelected,
    required this.child,
  });

  @override
  Widget build(BuildContext context) {
    final isWideScreen = MediaQuery.of(context).size.width > 600;

    if (isWideScreen) {
      return Row(
        children: [
          NavigationRail(
            selectedIndex: selectedIndex,
            onDestinationSelected: onDestinationSelected,
            labelType: NavigationRailLabelType.all,
            destinations: const [
              NavigationRailDestination(
                icon: Icon(Icons.home),
                label: Text('الرئيسية'),
              ),
              NavigationRailDestination(
                icon: Icon(Icons.church),
                label: Text('القداسات'),
              ),
              NavigationRailDestination(
                icon: Icon(Icons.book),
                label: Text('القراءات'),
              ),
              NavigationRailDestination(
                icon: Icon(Icons.music_note),
                label: Text('التسابيح'),
              ),
              NavigationRailDestination(
                icon: Icon(Icons.schedule),
                label: Text('الأجبية'),
              ),
              NavigationRailDestination(
                icon: Icon(Icons.settings),
                label: Text('الإعدادات'),
              ),
            ],
          ),
          const VerticalDivider(thickness: 1, width: 1),
          Expanded(child: child),
        ],
      );
    } else {
      return Column(
        children: [
          Expanded(child: child),
          NavigationBar(
            selectedIndex: selectedIndex,
            onDestinationSelected: onDestinationSelected,
            destinations: const [
              NavigationDestination(icon: Icon(Icons.home), label: 'الرئيسية'),
              NavigationDestination(icon: Icon(Icons.church), label: 'القداسات'),
              NavigationDestination(icon: Icon(Icons.book), label: 'القراءات'),
              NavigationDestination(icon: Icon(Icons.music_note), label: 'التسابيح'),
              NavigationDestination(icon: Icon(Icons.schedule), label: 'الأجبية'),
              NavigationDestination(icon: Icon(Icons.settings), label: 'الإعدادات'),
            ],
          ),
        ],
      );
    }
  }
}

