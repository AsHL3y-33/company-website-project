from typing import List
from weapon import Weapon
from Item import Item

# プレイヤー情報を管理するクラス
class Player:
    def __init__(self, name: str):
        self.name = name
        self.money = 10000
        self.equipment_storage: List[Weapon] = []
        self.permanent_inventory: List[Item] = []

    def buy_equipment(self, weapon: Weapon) -> None:
        # 武器購入処理
        if self.money >= weapon.value:
            self.money -= weapon.value
            self.equipment_storage.append(weapon)
            print(f"装備購入: {weapon.name} を購入しました。残高: ¥{self.money}")
        else:
            print("所持金不足で購入できません。")