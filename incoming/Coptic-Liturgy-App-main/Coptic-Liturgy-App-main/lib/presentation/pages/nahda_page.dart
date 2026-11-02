import 'package:coptic/presentation/pages/menu_page.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../core/utils/app_state_manager.dart';
import '../../core/utils/app_router.dart';
import 'virgin_mary_praises_page.dart';

class NahdaPage extends StatelessWidget {
  const NahdaPage({super.key});

  @override
  Widget build(BuildContext context) {
    final appState = Provider.of<AppStateManager>(context);

    return Scaffold(
      appBar: AppBar(title: const Text('نهضة العذراء')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            Expanded(
              child: GridView.count(
                crossAxisCount: 2,
                mainAxisSpacing: 16,
                crossAxisSpacing: 16,
                children: [
                  _buildOptionCard(
                    context,
                    title: 'العشية',
                    icon: Icons.wb_twilight,
                    onTap: () {
                      Navigator.pushNamed(
                        context,
                        AppRouter.content,
                        arguments: {
                          'title': 'عشية العذراء',
                          'jsonFile': 'asheyya_virgin.json',
                        },
                      );
                    },
                  ),
                  _buildOptionCard(
                    context,
                    title: 'التماجيد',
                    icon: Icons.star,
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => const VirginMaryPraisesPage(),
                        ),
                      );
                    },
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildOptionCard(
    BuildContext context, {
    required String title,
    required IconData icon,
    required VoidCallback onTap,
  }) {
    return Card(
      elevation: 4,
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(12),
        child: Container(
          padding: const EdgeInsets.all(16),
          decoration: BoxDecoration(borderRadius: BorderRadius.circular(12)),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Icon(icon, size: 48, color: Theme.of(context).primaryColor),
              const SizedBox(height: 16),
              Text(
                title,
                style: Theme.of(context).textTheme.titleLarge,
                textAlign: TextAlign.center,
              ),
            ],
          ),
        ),
      ),
    );
  }
}

