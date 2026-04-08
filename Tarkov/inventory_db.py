import sqlite3
from typing import Iterable
from Item import Item

DB_NAME = "inventory.db"

def init_db():
    conn = sqlite3.connect(DB_NAME)
    try:
        cursor = conn.cursor()
        cursor.execute("""
            CREATE TABLE IF NOT EXISTS inventory (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                quality TEXT NOT NULL,
                size INTEGER NOT NULL,
                value INTEGER NOT NULL
            )
        """)
        conn.commit()
    finally:
        conn.close()

def save_items(items: Iterable[Item]) -> None:
    conn = sqlite3.connect(DB_NAME)
    try:
        conn.execute("BEGIN")
        for item in items:
            conn.execute(
                "INSERT INTO inventory (name, quality, size, value) VALUES (?, ?, ?, ?)",
                (item.name, item.quality, item.size, item.value)
            )
        conn.commit()
        print("データベースに保存しました。")
    except Exception as e:
        conn.rollback()
        print("保存に失敗しました。ロールバックします。理由:", e)
    finally:
        conn.close()

def fetch_all_items():
    conn = sqlite3.connect(DB_NAME)
    try:
        cursor = conn.cursor()
        cursor.execute("SELECT id, name, quality, size, value FROM inventory ORDER BY id DESC")
        return cursor.fetchall()
    finally:
        conn.close()