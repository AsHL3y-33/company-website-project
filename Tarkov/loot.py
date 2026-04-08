from Item import Item

# 戦利品クラス: 探索で入手できる一般アイテム
class LootItem(Item):
    def __init__(self, name: str, quality: str, size: int, value: int):
        super().__init__(name, quality, size, value)

    def show_info(self) -> None:
        print(self)

    def __str__(self) -> str:
        # 特殊メソッド: 物資用の表示
        return f"物資: {self.name}（{self.quality}）サイズ:{self.size} 価値:¥{self.value}"