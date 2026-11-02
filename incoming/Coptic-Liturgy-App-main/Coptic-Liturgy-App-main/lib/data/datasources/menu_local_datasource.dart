import '../../domain/entities/menu_entity.dart';
import '../../core/utils/constants.dart';

abstract class MenuLocalDataSource {
  List<MenuEntity> getMenuItems(String category);
}

class MenuLocalDataSourceImpl implements MenuLocalDataSource {
  @override
  List<MenuEntity> getMenuItems(String category) {
    switch (category) {
      case AppConstants.mainCategory:
        return [
          MenuEntity(
            id: 'liturgies',
            title: AppConstants.liturgiesTitle,
            iconName: 'book',
            category: AppConstants.liturgiesCategory,
          ),
          MenuEntity(
            id: 'psalmody',
            title: AppConstants.psalmodyTitle,
            iconName: 'music_note',
            category: AppConstants.mainCategory,
            jsonFile: AppConstants.psalmodyFile,
          ),
          MenuEntity(
            id: 'readings',
            title: AppConstants.readingsTitle,
            iconName: 'book',
            category: AppConstants.mainCategory,
            jsonFile: AppConstants.readingsFile,
          ),
          MenuEntity(
            id: 'agpeya',
            title: AppConstants.agpeyaTitle,
            iconName: 'schedule',
            category: AppConstants.mainCategory,
            jsonFile: AppConstants.agpeyaFile,
          ),
          MenuEntity(
            id: 'occasions',
            title: 'مناسبات وأعياد',
            iconName: 'celebration',
            category: AppConstants.occasionsCategory,
          ),
        ];
      case AppConstants.liturgiesCategory:
        return [
          MenuEntity(
            id: 'basilian_liturgy',
            title: 'القداس الباسيلى',
            iconName: 'church',
            category: 'basilian_parts',
            jsonFile: 'basilian_liturgy.json',
          ),
          MenuEntity(
            id: 'gregorian_liturgy',
            title: 'القداس الغريغورى',
            iconName: 'church',
            category: 'gregorian_parts',
            jsonFile: 'gregorian_liturgy.json',
          ),
          MenuEntity(
            id: 'cyrillic_liturgy',
            title: 'القداس الكيرلسى',
            iconName: 'church',
            category: 'cyrillic_parts',
            jsonFile: 'cyrillic_liturgy.json',
          ),
          MenuEntity(
            id: 'distribution',
            title: 'التوزيع',
            iconName: 'calendar_today',
            category: 'distribution_parts',
            jsonFile: AppConstants.liturgyDistributionFile,
          ),
        ];
      case 'distribution_parts':
        return [
          MenuEntity(
            id: 'catechumens_liturgy',
            title: 'قداس الموعوظين',
            iconName: 'people',
            category: AppConstants.mainCategory,
            jsonFile: 'catechumens_liturgy.json',
          ),
          MenuEntity(
            id: 'believers_liturgy',
            title: 'قداس المؤمنين',
            iconName: 'group',
            category: AppConstants.mainCategory,
            jsonFile: 'believers_liturgy.json',
          ),
        ];
      case AppConstants.occasionsCategory:
        return [
          MenuEntity(
            id: 'virgin_mary_revival',
            title: 'نهضة العذراء',
            iconName: 'church',
            category: 'virgin_mary',
          ),
          MenuEntity(
            id: 'nativity_feast',
            title: 'عيد الميلاد',
            iconName: 'star',
            category: 'nativity',
            jsonFile: AppConstants.psalmodyFile, // مؤقت<lemma حتى ننشئ الملف
          ),
          MenuEntity(
            id: 'epiphany_feast',
            title: 'عيد الغطاس',
            iconName: 'water_drop',
            category: 'epiphany',
            jsonFile: AppConstants.readingsFile, // مؤقت<lemma حتى ننشئ الملف
          ),
        ];
      case 'virgin_mary':
        return [
          MenuEntity(
            id: 'virgin_mary_vespers',
            title: 'عشية نهضة العذراء',
            iconName: 'brightness_6',
            category: AppConstants.mainCategory,
            jsonFile: AppConstants.psalmodyFile, // مؤقت<lemma
          ),
          MenuEntity(
            id: 'virgin_mary_praises',
            title: 'تماجيد العذراء',
            iconName: 'music_note',
            category: 'virgin_mary_praises',
          ),
        ];
      case 'virgin_mary_praises':
        return [
          MenuEntity(
            id: 'praise_1',
            title: 'المديح الأول - يا مريم البكر',
            iconName: 'music_note',
            category: AppConstants.mainCategory,
            jsonFile: 'praise_1.json',
          ),
          MenuEntity(
            id: 'praise_2',
            title: 'المديح الثاني - السلام لك يا مريم',
            iconName: 'music_note',
            category: AppConstants.mainCategory,
            jsonFile: 'praise_2.json',
          ),
          MenuEntity(
            id: 'praise_3',
            title: 'المديح الثالث - أفرحي يا مريم',
            iconName: 'music_note',
            category: AppConstants.mainCategory,
            jsonFile: 'praise_3.json',
          ),
          MenuEntity(
            id: 'praise_4',
            title: 'المديح الرابع - يا أم النور',
            iconName: 'music_note',
            category: AppConstants.mainCategory,
            jsonFile: 'praise_4.json',
          ),
          MenuEntity(
            id: 'praise_5',
            title: 'المديح الخامس - العذراء الطاهرة',
            iconName: 'music_note',
            category: AppConstants.mainCategory,
            jsonFile: 'praise_5.json',
          ),
        ];
      default:
        return [];
    }
  }
}

