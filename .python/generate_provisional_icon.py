# -*- coding: utf-8 -*-
"""Deterministically renders the provisional launcher icon of AutoJs4 Runtime.

The maintainer has not supplied artwork yet. Until then this script draws a neutral glyph (the
digit 4 inside a ring) at 432 x 432 px with a transparent background, in a dark variant for
light themes (`mipmap/ic_launcher.png`) and a light variant for dark themes
(`mipmap-night/ic_launcher.png`). Replace the artwork through Icon Studio once it exists; do not
hand-edit the PNG files.

zh-CN: 确定性地渲染 AutoJs4 Runtime 的临时启动器图标. 维护者尚未提供图稿, 在此之前本脚本以透明背景在
432 x 432 px 画布上绘制中性图案 (圆环内的数字 4), 亮色主题用深色版本 (`mipmap/ic_launcher.png`),
暗色主题用浅色版本 (`mipmap-night/ic_launcher.png`). 图稿到位后经 Icon Studio 替换, 不要手工编辑 PNG.

Usage:
  py .python/generate_provisional_icon.py          # write both PNG files
  py .python/generate_provisional_icon.py --check  # verify the PNG files match this script
"""
from __future__ import annotations

import argparse
import io
import sys
from pathlib import Path

from PIL import Image, ImageDraw

ROOT = Path(__file__).resolve().parents[1]
SIZE = 432
SCALE = 4
TARGETS = {
    ROOT / "app/src/main/res/mipmap/ic_launcher.png": (0x27, 0x27, 0x27),
    ROOT / "app/src/main/res/mipmap-night/ic_launcher.png": (0xD8, 0xD8, 0xD8),
}


def render(color: tuple[int, int, int]) -> bytes:
    canvas = SIZE * SCALE
    image = Image.new("RGBA", (canvas, canvas), (0, 0, 0, 0))
    draw = ImageDraw.Draw(image)
    rgba = (*color, 255)
    center = canvas / 2
    outer = canvas * 0.44
    ring = canvas * 0.055
    draw.ellipse(
        (center - outer, center - outer, center + outer, center + outer),
        outline=rgba,
        width=int(ring),
    )
    # Digit 4 drawn as three strokes: a vertical bar, a diagonal and a crossbar.
    stroke = canvas * 0.075
    top = center - canvas * 0.22
    bottom = center + canvas * 0.22
    bar_x = center + canvas * 0.09
    cross_y = center + canvas * 0.07
    left_x = center - canvas * 0.19
    draw.line((bar_x, top, bar_x, bottom), fill=rgba, width=int(stroke))
    draw.line((bar_x, top, left_x, cross_y), fill=rgba, width=int(stroke))
    draw.line((left_x - stroke / 2, cross_y, center + canvas * 0.21, cross_y), fill=rgba, width=int(stroke))
    image = image.resize((SIZE, SIZE), Image.LANCZOS)
    # Re-apply the exact colour so anti-aliasing only affects alpha.
    alpha = image.getchannel("A")
    solid = Image.new("RGBA", (SIZE, SIZE), rgba)
    solid.putalpha(alpha)
    buffer = io.BytesIO()
    solid.save(buffer, format="PNG", optimize=True)
    return buffer.getvalue()


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description="Render the provisional AutoJs4 Runtime icon")
    parser.add_argument("--check", action="store_true", help="verify the PNG files without writing")
    arguments = parser.parse_args(argv)
    drift = []
    for path, color in TARGETS.items():
        expected = render(color)
        if arguments.check:
            if not path.is_file() or path.read_bytes() != expected:
                drift.append(str(path.relative_to(ROOT)))
        else:
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_bytes(expected)
            print(f"Generated {path.relative_to(ROOT)}")
    if drift:
        print("ICON_ERROR stale -> " + "; ".join(drift))
        return 1
    print("ICON_OK")
    return 0


if __name__ == "__main__":
    sys.exit(main())
