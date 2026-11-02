import 'package:flutter/material.dart';
import '../../data/services/config_service.dart';

class PraisesPage extends StatelessWidget {
  final String title;

  const PraisesPage({
    super.key,
    required this.title,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: FutureBuilder<Map<String, dynamic>>(
        future: ConfigService.instance.loadContent('psalmody.json'),
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
          } else if (!snapshot.hasData) {
            return const Center(child: Text('لا يوجد محتوى'));
          }

          final content = snapshot.data!;
          final items = content['items'] as List<dynamic>? ?? [];

          return ListView.builder(
            padding: const EdgeInsets.all(16),
            itemCount: items.length,
            itemBuilder: (context, index) {
              final item = items[index] as Map<String, dynamic>;
              return Card(
                margin: const EdgeInsets.only(bottom: 8),
                child: ListTile(
                  leading: const Icon(Icons.music_note),
                  title: Text(item['title'] ?? ''),
                  subtitle: Text(item['description'] ?? ''),
                  onTap: () {
                    // Navigate to content page
                  },
                ),
              );
            },
          );
        },
      ),
    );
  }
}

