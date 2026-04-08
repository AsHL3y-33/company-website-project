# 脱出地点クラス: Boss出現率を持つ
class EscapeZone:
    def __init__(self, name: str, boss_chance: float):
        self.name = name
        self.boss_chance = boss_chance  # 0.0〜1.0（例: 0.3 は 30%）