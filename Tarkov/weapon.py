from Item import Item

# 武器クラス: 脱出ボーナスを持つアイテム
class Weapon(Item):
    def __init__(self, name: str, quality: str, price: int, size: int = 2):
        # price は Item.value に格納
        super().__init__(name, quality, size, price)

    def escape_bonus(self) -> int:
        # 品質に応じた脱出成功率のボーナス
        bonus_map = {"灰色": 0, "緑色": 5, "橙色": 15, "赤色": 30}
        return bonus_map.get(self.quality, 0)

    def show_info(self) -> None:
        print(self)

    def __str__(self) -> str:
        # 特殊メソッド: 武器用の表示
        return f"武器: {self.name}（{self.quality}）価格: ¥{self.value}"