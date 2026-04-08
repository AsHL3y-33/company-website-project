import random
from loot import LootItem

# 探索ポイント: ランダムに戦利品を返す
class InteractionPoint:
    def __init__(self):
        self.loot_pool = [
            LootItem("古い電池", "灰色", 1, 300),
            LootItem("医療パック", "緑色", 2, 800),
            LootItem("グラフィックカード", "橙色", 3, 3000),
            LootItem("金のネックレス", "赤色", 2, 5000),
        ]

    def search(self) -> LootItem:
        item = random.choice(self.loot_pool)
        print("探索結果:")
        item.show_info()
        return item

# 部屋クラス: 複数の探索ポイントを持つ
class Room:
    def __init__(self, name: str, num_points: int):
        self.name = name
        self.points = [InteractionPoint() for _ in range(num_points)]

    def explore(self):
        print(f"部屋: {self.name}")
        for idx, point in enumerate(self.points, start=1):
            print(f"探索ポイント {idx}:")
            yield point.search()

# 探索地点クラス: 複数の部屋を持つ
class Location:
    def __init__(self, name: str, rooms):
        self.name = name
        self.rooms = rooms

    def enter(self):
        print(f"探索地点: {self.name}")
        for room in self.rooms:
            for item in room.explore():
                yield item