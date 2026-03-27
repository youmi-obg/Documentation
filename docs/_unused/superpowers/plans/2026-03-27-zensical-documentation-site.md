# Zensical 文档站点实现计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 使用 Zensical 静态站点生成器将现有的 Markdown 对接文档转换为现代化、可搜索、响应式的文档网站

**Architecture:** 分两个阶段实施：1) 初始化 Zensical 并迁移现有内容 2) 优化内容结构和展示效果

**Tech Stack:** Zensical (基于 MkDocs Material), Python, Markdown, GitHub Pages

---

## 文件结构映射

| 文件 | 操作 | 说明 |
|------|------|------|
| `zensical.yml` | 创建 | Zensical 主配置文件 |
| `docs/` 目录 | 创建 | 存放 Markdown 文档源文件 |
| `docs/index.md` | 创建 | 站点首页 |
| `docs/zh/` | 创建 | 中文文档目录 |
| `docs/en/` | 创建 | 英文文档目录 |
| `README.md` | 修改 | 添加新站点说明 |
| `.gitignore` | 创建/修改 | 忽略 Python 虚拟环境和构建产物 |

---

## 任务清单

### Task 1: 环境准备 - 安装 Zensical

**Files:**
- Create: `.gitignore`
- Modify: (无新文件，系统级安装)

- [ ] **Step 1: 创建 .gitignore 文件**

```gitignore
# Python
__pycache__/
*.py[cod]
*$py.class
*.so
.Python
env/
venv/
ENV/
build/
develop-eggs/
dist/
downloads/
eggs/
.eggs/
lib/
lib64/
parts/
sdist/
var/
wheels/
*.egg-info/
.installed.cfg
*.egg

# Zensical / MkDocs
site/
```

- [ ] **Step 2: 检查 Python 版本**

Run: `python3 --version`
Expected: Python 3.8+

- [ ] **Step 3: 创建虚拟环境并安装 Zensical**

Run:
```bash
python3 -m venv .venv
source .venv/bin/activate
pip install zensical
```

- [ ] **Step 4: 验证安装**

Run: `zensical --version`
Expected: zensical x.y.z

- [ ] **Step 5: 提交**

```bash
git add .gitignore
git commit -m "chore: add .gitignore for Python/Zensical"
```

---

### Task 2: 初始化 Zensical 项目

**Files:**
- Create: `zensical.yml`
- Create: `docs/index.md`

- [ ] **Step 1: 初始化 Zensical 项目**

Run:
```bash
source .venv/bin/activate
zensical new
```

(使用默认选项，项目名称设为 "Youmi Documentation")

- [ ] **Step 2: 查看生成的文件结构**

Run: `ls -la`
Expected: 看到 `zensical.yml` 和 `docs/` 目录

- [ ] **Step 3: 配置 zensical.yml - 基础设置**

将以下内容写入 `zensical.yml`:

```yaml
site_name: Youmi 对接文档
site_url: https://youmi-obg.github.io/Documentation/
site_author: Youmi
site_description: 有米海外广告 SDK 对接文档

theme:
  name: material
  language: zh
  palette:
    - media: "(prefers-color-scheme: light)"
      scheme: default
      primary: indigo
      accent: indigo
      toggle:
        icon: material/weather-night
        name: 切换深色模式
    - media: "(prefers-color-scheme: dark)"
      scheme: slate
      primary: indigo
      accent: indigo
      toggle:
        icon: material/weather-sunny
        name: 切换浅色模式
  features:
    - navigation.tabs
    - navigation.sections
    - navigation.expand
    - navigation.path
    - navigation.indexes
    - search.suggest
    - search.highlight
    - content.code.copy
    - content.code.select

markdown_extensions:
  - admonition
  - pymdownx.details
  - pymdownx.superfences
  - pymdownx.highlight:
      anchor_linenums: true
  - pymdownx.inlinehilite
  - pymdownx.snippets
  - pymdownx.keys
  - attr_list
  - md_in_html
  - tables
  - toc:
      permalink: true

plugins:
  - search:
      lang:
        - en
        - zh

nav:
  - 首页: index.md
  - Android SDK:
    - 中文: zh/android-offerwall.md
    - English: en/android-offerwall.md
  - API 文档:
    - Server API: zh/sdk-api.md
    - Network Offline API: zh/network-offline-api.md
  - 互动广告:
    - SpinGo: zh/spingo.md
    - WebView 对接: zh/interactive-ads-webview.md
    - Flutter WebView: zh/interactive-ads-webview-flutter.md
  - Flutter SDK:
    - 中文: zh/flutter-sdk.md
```

- [ ] **Step 4: 创建 docs/index.md 首页**

```markdown
# Youmi 对接文档

欢迎使用有米海外广告 SDK 对接文档！

## 快速开始

选择您需要对接的产品：

<div class="grid cards" markdown>

-   :material-android: __Android OfferWall SDK__

    ---

    安卓积分墙 SDK 对接指南

    [中文 →](zh/android-offerwall.md)
    [English →](en/android-offerwall.md)

-   :material-flutter: __Flutter SDK__

    ---

    Flutter 插件对接文档

    [查看 →](zh/flutter-sdk.md)

-   :material-api: __Server API__

    ---

    服务器端 API 接口文档

    [查看 →](zh/sdk-api.md)

-   :material-web: __互动广告__

    ---

    SpinGo 互动广告接入指南

    [查看 →](zh/spingo.md)

</div>

## 联系方式

如有任何问题，请联系我们：

- **Email**: mkt@youmi.net
- **WhatsApp**: +86 180 2853 9642
- **开发者后台**: https://offers.youmi.net/
```

- [ ] **Step 5: 启动本地预览服务器**

Run:
```bash
source .venv/bin/activate
zensical serve
```

Expected: 服务器在 http://127.0.0.1:8000 启动

- [ ] **Step 6: 提交**

```bash
git add zensical.yml docs/index.md
git commit -m "feat: initialize zensical project with base config"
```

---

### Task 3: 迁移中文文档

**Files:**
- Create: `docs/zh/android-offerwall.md`
- Create: `docs/zh/sdk-api.md`
- Create: `docs/zh/network-offline-api.md`
- Create: `docs/zh/spingo.md`
- Create: `docs/zh/interactive-ads-webview.md`
- Create: `docs/zh/interactive-ads-webview-flutter.md`
- Create: `docs/zh/flutter-sdk.md`

- [ ] **Step 1: 创建中文文档目录**

Run: `mkdir -p docs/zh docs/en`

- [ ] **Step 2: 迁移 AndroidOfferWall_cn.md → docs/zh/android-offerwall.md**

复制内容并优化格式，修复代码块标记为 `gradle`、`kotlin`、`java` 等，添加标题层级优化。

- [ ] **Step 3: 迁移 SDKAPI.md → docs/zh/sdk-api.md**

复制内容，为 JSON 示例添加 `json` 语言标记，优化表格展示。

- [ ] **Step 4: 迁移 NetworkOfflineAPI.md → docs/zh/network-offline-api.md**

复制内容，格式化 API 文档。

- [ ] **Step 5: 迁移 SpinGo_cn.md → docs/zh/spingo.md**

复制内容，优化图片展示。

- [ ] **Step 6: 迁移 InteractiveAdsWebView_cn.md → docs/zh/interactive-ads-webview.md**

复制内容。

- [ ] **Step 7: 迁移 InteractiveAdsWebViewFlutter_cn.md → docs/zh/interactive-ads-webview-flutter.md**

复制内容。

- [ ] **Step 8: 迁移 sdk_flutter对接文档.md → docs/zh/flutter-sdk.md**

复制内容。

- [ ] **Step 9: 移动 images 目录到 docs/**

Run: `mv images docs/images`

- [ ] **Step 10: 更新文档中的图片路径**

将所有 `./images/` 替换为 `../images/` （如果在 docs/zh/ 子目录中）

- [ ] **Step 11: 本地预览验证中文文档**

Run: `zensical serve`
Verify: 访问 http://127.0.0.1:8000 检查中文文档

- [ ] **Step 12: 提交**

```bash
git add docs/zh/ docs/images/
git rm -r images/
git commit -m "feat: migrate Chinese documentation to zensical"
```

---

### Task 4: 迁移英文文档

**Files:**
- Create: `docs/en/android-offerwall.md`
- Create: `docs/en/sdk-api.md`
- Create: `docs/en/network-offline-api.md`
- Create: `docs/en/spingo.md`
- Create: `docs/en/interactive-ads-webview.md`
- Create: `docs/en/interactive-ads-webview-flutter.md`

- [ ] **Step 1: 迁移 AndroidOfferWall.md → docs/en/android-offerwall.md**

- [ ] **Step 2: 迁移 SDKAPI.md (如果有英文版) 或标注英文版本**

(注：当前 SDKAPI.md 已是英文)

- [ ] **Step 3: 迁移 NetworkOfflineAPI.md → docs/en/network-offline-api.md**

- [ ] **Step 4: 迁移 SpinGo.md → docs/en/spingo.md**

- [ ] **Step 5: 迁移 InteractiveAdsWebView.md → docs/en/interactive-ads-webview.md**

- [ ] **Step 6: 迁移 InteractiveAdsWebviewFlutter.md → docs/en/interactive-ads-webview-flutter.md**

- [ ] **Step 7: 更新 zensical.yml 的导航配置**

确保英文文档在 nav 中正确配置。

- [ ] **Step 8: 本地预览验证英文文档**

- [ ] **Step 9: 提交**

```bash
git add docs/en/
git commit -m "feat: migrate English documentation to zensical"
```

---

### Task 5: 配置多语言支持 (i18n)

**Files:**
- Modify: `zensical.yml`

- [ ] **Step 1: 更新 zensical.yml 配置多语言**

修改配置添加语言切换支持：

```yaml
plugins:
  - search:
      lang:
        - en
        - zh
  - i18n:
      default_language: zh
      docs_structure: suffix
      languages:
        zh:
          name: 中文
          build: true
        en:
          name: English
          build: true
```

- [ ] **Step 2: 调整文档目录结构为后缀模式**

或使用目录模式，选择更简单的方案。

- [ ] **Step 3: 测试语言切换**

- [ ] **Step 4: 提交**

```bash
git add zensical.yml
git commit -m "feat: add i18n multilingual support"
```

---

### Task 6: 构建和部署配置

**Files:**
- Create: `.github/workflows/deploy.yml`
- Modify: `zensical.yml` (add site_url)

- [ ] **Step 1: 创建 GitHub Actions 部署工作流**

创建 `.github/workflows/deploy.yml`:

```yaml
name: Deploy documentation

on:
  push:
    branches:
      - main
  workflow_dispatch:

permissions:
  contents: write
  pages: write
  id-token: write

concurrency:
  group: "pages"
  cancel-in-progress: false

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Configure Python
        uses: actions/setup-python@v5
        with:
          python-version: "3.11"

      - name: Install dependencies
        run: |
          python -m venv .venv
          source .venv/bin/activate
          pip install zensical

      - name: Build site
        run: |
          source .venv/bin/activate
          zensical build

      - name: Upload artifact
        uses: actions/upload-pages-artifact@v3
        with:
          path: site/

  deploy:
    environment:
      name: github-pages
      url: ${{ steps.deployment.outputs.page_url }}
    runs-on: ubuntu-latest
    needs: build
    steps:
      - name: Deploy to GitHub Pages
        id: deployment
        uses: actions/deploy-pages@v4
```

- [ ] **Step 2: 确认 zensical.yml 中的 site_url 正确**

```yaml
site_url: https://youmi-obg.github.io/Documentation/
```

- [ ] **Step 3: 在 GitHub 仓库设置中启用 GitHub Pages**

设置 → Pages → Build and deployment → Source: GitHub Actions

- [ ] **Step 4: 本地构建测试**

Run:
```bash
source .venv/bin/activate
zensical build
ls -la site/
```

- [ ] **Step 5: 提交**

```bash
git add .github/workflows/deploy.yml
git commit -m "feat: add GitHub Actions deploy workflow"
```

---

### Task 7: 内容优化第一阶段 - 改进现有内容

**Files:**
- Modify: `docs/zh/android-offerwall.md`
- Modify: `docs/zh/sdk-api.md`
- Modify: 其他文档文件

- [ ] **Step 1: 为 Android 文档添加警告提示框**

使用 Admonition 标记重要注意事项：

```markdown
!!! warning "注意"
    启用 SDK 的时候需要带上用户的唯一 id，userId。用户 ID 之后可用于结算，在 Postback 配置可以配置 `{aff_sub}` 给开发者回传。
```

- [ ] **Step 2: 优化代码示例**

确保所有代码块都有正确的语言标记，例如 `kotlin`、`gradle`、`json`、`java` 等。

- [ ] **Step 3: 为 API 文档添加请求/响应示例折叠框**

使用 Details 扩展：

```markdown
??? example "请求示例"
    ```http
    GET /v1/aff/offers?app_id=xxx&os=android HTTP/1.1
    Host: api.mygo-free.com
    ```

??? success "响应示例"
    ```json
    {
      "c": 0,
      "d": { ... }
    }
    ```
```

- [ ] **Step 4: 添加页脚导航链接**

在各个文档底部添加"上一页/下一页"或相关文档链接。

- [ ] **Step 5: 本地预览验证优化效果**

- [ ] **Step 6: 提交**

```bash
git add docs/zh/ docs/en/
git commit -m "refactor: improve content formatting with admonitions and details"
```

---

### Task 8: 更新主 README 和清理旧文件

**Files:**
- Modify: `README.md`
- Delete/Archive: 根目录下的旧 .md 文件

- [ ] **Step 1: 更新 README.md**

```markdown
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
```

- [ ] **Step 2: 创建 archive 目录并移动旧文档（可选）**

或者保留旧文件但在 README 中说明已废弃。

- [ ] **Step 3: 提交**

```bash
git add README.md
git commit -m "docs: update README with zensical site info"
```

---

## 计划审查

### 1. 规格覆盖检查
- ✅ Zensical 安装和初始化 - Task 1-2
- ✅ 中文文档迁移 - Task 3
- ✅ 英文文档迁移 - Task 4
- ✅ 多语言支持 - Task 5
- ✅ GitHub Pages 部署 - Task 6
- ✅ 内容优化 - Task 7
- ✅ 项目清理 - Task 8

### 2. 占位符扫描
- 无 TBD/TODO
- 所有代码块均有实际内容
- 所有命令都明确

### 3. 类型一致性检查
- 文件名一致
- 路径一致
- 命令一致

---

## 执行交接

计划已保存至 `docs/superpowers/plans/2026-03-27-zensical-documentation-site.md`。

**两种执行方式：**

**1. Subagent-Driven (推荐)** - 为每个任务调度新的子代理，任务间进行审查，快速迭代

**2. Inline Execution** - 在当前会话中使用 executing-plans 执行任务，带检查点的批量执行

选择哪种方式？
