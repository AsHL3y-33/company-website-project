import random
from typing import List
from Backpack import Backpack
from escape import EscapeZone
from inventory_db import save_items
from inventory_csv import save_items_csv

# ゲームセッション: 1回の探索と脱出を管理
class GameSession:
    def __init__(self, player, backpack_level: int):
        self.player = player
        self.backpack = Backpack(backpack_level)

    def attempt_escape(self, escape_zone: EscapeZone) -> None:
        total_bonus = sum(w.escape_bonus() for w in self.player.equipment_storage)
        escape_chance = 50 + total_bonus
        boss_roll = random.random()

        print(f"脱出地点: {escape_zone.name}")
        print(f"Boss出現率: {escape_zone.boss_chance*100:.1f}%")
        print(f"脱出成功率: {escape_chance}%")

        if boss_roll < escape_zone.boss_chance:
            print("Bossに遭遇し失敗。バックパックの物資は消失します。（保険箱は安全）")
            self.backpack.items.clear()
        else:
            print("脱出成功！保険箱とバックパックの物資を持ち帰りました。")
            items = self.backpack.safe_box + self.backpack.items
            self.player.permanent_inventory.extend(items)

            # 外部技術①: SQLite 保存（トランザクション）
            save_items(items)
            # 外部技術②: CSV ファイル保存
            save_items_csv(items)

            self.backpack.items.clear()
            self.backpack.safe_box.clear()