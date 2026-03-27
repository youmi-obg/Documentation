# Youmi Documentation

有米海外广告 SDK 对接文档

## 📖 在线阅读

文档已使用 Zensical 构建并部署到 GitHub Pages：

**👉 https://youmi-obg.github.io/Documentation/**

## 本地开发

```bash
# 克隆仓库
git clone https://github.com/youmi-obg/Documentation.git
cd Documentation

# 创建虚拟环境并安装依赖
python3 -m venv .venv
source .venv/bin/activate
pip install zensical

# 本地预览
zensical serve

# 构建
zensical build
```

## 文档结构

```
docs/
├── index.md          # 首页
├── zh/               # 中文文档
│   ├── android-offerwall.md
│   ├── sdk-api.md
│   ├── spingo.md
│   └── ...
├── en/               # 英文文档
│   ├── android-offerwall.md
│   └── ...
└── images/           # 图片资源
```

## 旧文档

根目录下的 `.md` 文件为旧版本文档，已迁移至 `docs/` 目录下。
