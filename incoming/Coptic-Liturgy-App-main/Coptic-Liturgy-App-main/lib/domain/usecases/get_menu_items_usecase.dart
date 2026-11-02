import '../entities/menu_entity.dart';
import '../repositories/menu_repository.dart';

class GetMenuItemsUseCase {
  final MenuRepository repository;

  GetMenuItemsUseCase(this.repository);

  List<MenuEntity> execute(String category) {
    return repository.getMenuItems(category);
  }
}
