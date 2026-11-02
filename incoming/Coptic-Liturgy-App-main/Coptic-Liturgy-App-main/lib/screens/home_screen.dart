// import 'package:coptic/screens/widgets/content_tile.dart';
// import 'package:flutter/material.dart';

// import 'content_screen.dart';

// class HomeScreen extends StatelessWidget {
//   const HomeScreen({super.key});

//   final List<Map<String, String>> items = const [
//     {
//       'title': 'القداسات',
//       'image': 'assets/images/liturgies.png',
//       'file': 'التوزيع_السنوى.json',
//     },
//     {
//       'title': 'التسبحة',
//       'image': 'assets/images/psalmody.png',
//       'file': 'psalmody.json',
//     },
//     {
//       'title': 'القراءات',
//       'image': 'assets/images/readings.png',
//       'file': 'readings.json',
//     },
//     {
//       'title': 'الأجبية',
//       'image': 'assets/images/agpeya.png',
//       'file': 'agpeya.json',
//     },
//   ];

//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: AppBar(title: const Text('المكتبة الطقسية')),
//       drawer: Drawer(
//         child: ListView(
//           padding: EdgeInsets.zero,
//           children: [
//             const DrawerHeader(
//               decoration: BoxDecoration(color: Colors.brown),
//               child: Center(
//                 child: Text(
//                   'القائمة',
//                   style: TextStyle(color: Colors.white, fontSize: 24),
//                 ),
//               ),
//             ),
//             ListTile(
//               leading: const Icon(Icons.book),
//               title: const Text('جدول المحتويات'),
//               onTap: () {},
//             ),
//             ListTile(
//               leading: const Icon(Icons.settings),
//               title: const Text('الإعدادات'),
//               onTap: () {},
//             ),
//             ListTile(
//               leading: const Icon(Icons.search),
//               title: const Text('بحث'),
//               onTap: () {},
//             ),
//           ],
//         ),
//       ),
//       body: GridView.builder(
//         padding: const EdgeInsets.all(16),
//         itemCount: items.length,
//         gridDelegate: const SliverGridDelegateWithMaxCrossAxisExtent(
//           maxCrossAxisExtent: 250, // 👈 أقصى عرض للبلاطة
//           mainAxisSpacing: 16,
//           crossAxisSpacing: 16,
//           childAspectRatio: 0.75, // 👈 عدد الأعمدة
//         ),
//         itemBuilder: (context, index) {
//           final item = items[index];
//           return ContentTile(
//             title: item['title']!,
//             //imagePath: item['image']!,
//             imagePath: item['image']!,
//             onTap: () {
//               Navigator.push(
//                 context,
//                 MaterialPageRoute(
//                   builder:
//                       (_) => ContentScreen(
//                         jsonFile: item['file']!,
//                         title: item['title']!,
//                       ),
//                 ),
//               );
//             },
//           );
//         },
//       ),
//     );
//   }
// }
