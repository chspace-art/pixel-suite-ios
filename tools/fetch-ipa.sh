#!/usr/bin/env bash
# 从最近一次成功的 GitHub Actions 运行里取回未签名 IPA。
#
#   ./tools/fetch-ipa.sh              # 取最近一次成功运行的产物到 dist/
#   ./tools/fetch-ipa.sh --watch      # 先看最近几次运行的状态
#   ./tools/fetch-ipa.sh --filebox    # 取完顺手推到云端文件箱，手机上直接下
#
# 需要 gh CLI（winget install GitHub.cli）且已 gh auth login。
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

FILEBOX="/c/software/tools/filebox.mjs"
WATCH=0
PUSH=0
for arg in "$@"; do
  case "$arg" in
    --watch) WATCH=1 ;;
    --filebox) PUSH=1 ;;
    *) echo "unknown arg: $arg" >&2; exit 2 ;;
  esac
done

command -v gh >/dev/null || { echo "gh CLI 没装：winget install GitHub.cli" >&2; exit 1; }

echo "== 最近几次运行 =="
gh run list --workflow ios-unsigned.yml --limit 5

if [ "$WATCH" = "1" ]; then exit 0; fi

echo
echo "== 取产物 =="
rm -rf dist
gh run download --name unsigned-ipa --dir dist --pattern '*.ipa' 2>/dev/null \
  || gh run download --name unsigned-ipa --dir dist

echo
ls -lh dist/

if [ "$PUSH" = "1" ]; then
  echo
  echo "== 推到云端文件箱 =="
  for f in dist/*.ipa; do
    node "$FILEBOX" push "$f"
  done
  echo "把上面给出的链接填进 SideStore 的 URL 安装入口即可。"
fi

cat <<'EOF'

接下来（手机侧）：
  1. 把 IPA 弄到 iPhone 上（上面 --filebox 的链接，或 Release 资产 URL）
  2. SideStore → 安装 → 选这个 IPA
  3. 首次装完，设置里信任开发者证书；LocalDevVPN 保持开着

名额提醒：免费 Apple ID 同时只能装 3 个自签 App，SideStore 自己占一个。
7 天到期后 App 会打不开——原地覆盖升级能保数据，别卸载重装。
EOF
