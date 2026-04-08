from typing import List
from Item import Item

# バックパック: 探索中の所持品管理
class Backpack:
    def __init__(self, level: int):
        self.level = level
        self.capacity = {1: 9, 2: 18, 3: 27}.get(level, 9)
        self.items: List[Item] = []
        self.safe_box: List[Item] = []

    def add_item(self, item: Item, to_safe: bool = False) -> bool:
        # アイテムを保険箱 or バックパックへ追加（成功=True/失敗=False）
        if to_safe:
            if self.get_safe_size() + item.size <= 9:
                self.safe_box.append(item)
                print("保険箱に追加しました。")
                return True
            print("保険箱の容量不足です。")
            return False
        if self.get_bag_size() + item.size <= self.capacity:
            self.items.append(item)
            print("バックパックに追加しました。")
            return True
        print("バックパックの容量不足です。")
        return False

    def get_bag_size(self) -> int:
        return sum(i.size for i in self.items)

    def get_safe_size(self) -> int:
        return sum(i.size for i in self.safe_box)