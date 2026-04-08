from abc import ABC, abstractmethod

# 抽象クラス: アイテム共通のインターフェースを定義
class Item(ABC):
    def __init__(self, name: str, quality: str, size: int, value: int):
        # 共通プロパティ
        self.name = name       # 名称
        self.quality = quality # 品質（灰色/緑色/橙色/赤色 など）
        self.size = size       # サイズ（占有マス数）
        self.value = value     # 価値/価格

    @abstractmethod
    def show_info(self) -> None:
        """抽象メソッド: 子クラスで必ず実装（表示処理）"""
        pass

    def __str__(self) -> str:
        # 特殊メソッド: print(item) の出力内容
        return f"{self.name}（{self.quality}）サイズ:{self.size} 価値:¥{self.value}"