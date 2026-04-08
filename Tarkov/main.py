from Player import Player
from weapon import Weapon
from session import GameSession
from escape import EscapeZone
from location import Location, Room
from inventory_db import init_db, fetch_all_items
from inventory_csv import init_csv

# アイテム収納先を対話的に決める（失敗時は再質問）
def place_item_interactively(session: GameSession, item) -> None:
    while True:
        print("\nこのアイテムをどこに入れますか？")
        item.show_info()
        print("1. 保険箱（最大9マス、脱出失敗でも持ち帰り）")
        print("2. バックパック（容量はレベル依存）")
        print("9. スキップ（収納しない）")
        choice = input("番号を入力: ").strip()
        if choice == "1":
            if session.backpack.add_item(item, to_safe=True):
                return
        elif choice == "2":
            if session.backpack.add_item(item, to_safe=False):
                return
        elif choice == "9":
            print("このアイテムの収納をスキップしました。")
            return
        else:
            print("無効な選択です。")

def main():
    print("Escape風ゲームへようこそ！")
    player_name = input("プレイヤー名を入力してください: ").strip() or "プレイヤー1"
    player = Player(player_name)

    # 外部技術初期化
    init_db()
    init_csv()

    escape_zones = [
        EscapeZone("工場エリア", 0.30),
        EscapeZone("森林地帯", 0.20),
        EscapeZone("港湾施設", 0.40),
    ]

    locations = [
        Location("廃墟ビル", [Room("ロビー", 2), Room("倉庫", 3)]),
        Location("研究所", [Room("ラボA", 2), Room("ラボB", 2)]),
        Location("ショッピングモール", [Room("食品売り場", 2), Room("電化製品売り場", 3)]),
    ]

    while True:
        print("\nメニュー")
        print("1. 装備を購入する")
        print("2. 探索して脱出を試みる")
        print("3. 永久インベントリを表示する")
        print("4. DB保存内容を確認する")
        print("9. 終了する")
        cmd = input("番号を入力: ").strip()

        if cmd == "1":
            shop = [
                Weapon("AK-74", "灰色", 3000),
                Weapon("M4A1", "緑色", 5000),
                Weapon("MP7", "橙色", 8000),
                Weapon("SR-25", "赤色", 12000),
            ]
            print("\nショップ一覧:")
            for i, w in enumerate(shop, start=1):
                print(f"{i}. {w}")
            try:
                idx = int(input("購入する番号を入力: ").strip()) - 1
                if 0 <= idx < len(shop):
                    player.buy_equipment(shop[idx])
                else:
                    print("無効な番号です。")
            except ValueError:
                print("数値を入力してください。")

        elif cmd == "2":
            try:
                lvl = int(input("バックパックのレベル（1〜3）: ").strip())
                if lvl not in (1, 2, 3):
                    print("1〜3の数字を選んでください。")
                    continue
            except ValueError:
                print("数値を入力してください。")
                continue

            session = GameSession(player, lvl)

            print("\n探索地点:")
            for i, loc in enumerate(locations, start=1):
                print(f"{i}. {loc.name}")
            try:
                loc_idx = int(input("探索地点番号を入力: ").strip()) - 1
                if not (0 <= loc_idx < len(locations)):
                    print("無効な番号です。")
                    continue
            except ValueError:
                print("数値を入力してください。")
                continue

            for item in locations[loc_idx].enter():
                place_item_interactively(session, item)

            print("\n脱出地点:")
            for i, zone in enumerate(escape_zones, start=1):
                print(f"{i}. {zone.name}（Boss出現率: {zone.boss_chance*100:.1f}%）")
            try:
                zidx = int(input("脱出地点番号: ").strip()) - 1
                if 0 <= zidx < len(escape_zones):
                    session.attempt_escape(escape_zones[zidx])
                else:
                    print("無効な番号です。")
            except ValueError:
                print("数値を入力してください。")

        elif cmd == "3":
            print("\n永久インベントリ:")
            if not player.permanent_inventory:
                print("持ち帰りアイテムはありません。")
            else:
                for it in player.permanent_inventory:
                    it.show_info()

        elif cmd == "4":
            rows = fetch_all_items()
            if not rows:
                print("データベースに保存されたアイテムはありません。")
            else:
                print("\nデータベース保存一覧:")
                for rid, name, quality, size, value in rows:
                    print(f"[{rid}] {name}（{quality}）サイズ:{size} 価値:¥{value}")

        elif cmd == "9":
            print("ゲームを終了します。")
            break

        else:
            print("無効な選択です。")

if __name__ == "__main__":
    main()