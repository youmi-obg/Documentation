---
title: 部署指南
---

# 部署指南

本文档说明如何部署本文档站点到 GitHub Pages。

## 前提条件

- 拥有仓库的写入权限
- GitHub Pages 已在仓库设置中启用

## 启用 GitHub Pages

1. 访问仓库设置页面：`https://github.com/[用户名]/Documentation/settings/pages`

2. 在 **Build and deployment** 部分：
   - **Source** 选择 **GitHub Actions**
   - 点击 **Save**

## 自动部署

本文档已配置 GitHub Actions 工作流，当代码推送到 `main` 分支时会自动构建和部署。

工作流文件位置：`.github/workflows/deploy.yml`

## 手动触发部署

如需手动触发部署：

1. 访问仓库的 Actions 页面
2. 选择 "Deploy documentation" 工作流
3. 点击 "Run workflow"
4. 选择分支并点击 "Run workflow"

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

## 测试部署

在正式部署到主仓库前，可以先推送到个人 fork 进行测试：

1. Fork 本仓库
2. 克隆你的 fork
3. 修改 `zensical.toml` 中的 `site_url` 为你的 fork 地址
4. 推送到你的 fork
5. 在你的 fork 中启用 GitHub Pages
6. 验证部署成功后，将 `site_url` 改回原地址并提交 PR

## 部署地址

正式部署地址：https://youmi-obg.github.io/Documentation/
