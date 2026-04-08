import csv
import os
from typing import Iterable
from Item import Item

CSV_NAME = "inventory.csv"

def init_csv():
    if not os.path.exists(CSV_NAME):
        with open(CSV_NAME, "w", newline="", encoding="utf-8") as f:
            writer = csv.writer(f)
            writer.writerow(["name", "quality", "size", "value"])

def save_items_csv(items: Iterable[Item]) -> None:
    with open(CSV_NAME, "a", newline="", encoding="utf-8") as f:
        writer = csv.writer(f)
        for item in items:
            writer.writerow([item.name, item.quality, item.size, item.value])