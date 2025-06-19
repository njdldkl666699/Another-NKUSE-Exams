// 动态文件结构生成脚本
// 该脚本可以在Node.js环境中运行，生成文件结构JSON

const fs = require("fs");
const path = require("path");

function scanDirectory(dirPath, basePath = "") {
  const result = {};
  const files = [];

  try {
    const items = fs.readdirSync(dirPath, { withFileTypes: true });

    for (const item of items) {
      const itemPath = path.join(dirPath, item.name);

      if (item.isDirectory()) {
        // 忽略隐藏文件夹和系统文件夹
        if (
          !item.name.startsWith(".") &&
          !item.name.startsWith("node_modules")
        ) {
          result[item.name] = scanDirectory(
            itemPath,
            path.join(basePath, item.name)
          );
        }
      } else if (item.isFile()) {
        // 忽略隐藏文件和一些不需要的文件
        if (
          !item.name.startsWith(".") &&
          !item.name.endsWith(".js") &&
          !item.name.endsWith(".html") &&
          !item.name.endsWith(".yml") &&
          !item.name.endsWith(".yaml") &&
          !item.name.endsWith(".json") &&
          !item.name.endsWith(".md")
        ) {
          files.push(item.name);
        }
      }
    }

    if (files.length > 0) {
      result.files = files;
    }
  } catch (error) {
    console.error(`Error scanning directory ${dirPath}:`, error.message);
  }

  return result;
}

function generateFileStructure() {
  const projectRoot = process.cwd();
  const softwareCoursesPath = path.join(projectRoot, "软件专业课");
  const generalCoursesPath = path.join(projectRoot, "通识必修课");

  const structure = {};

  // 扫描软件专业课目录
  if (fs.existsSync(softwareCoursesPath)) {
    structure["软件专业课"] = scanDirectory(softwareCoursesPath);
  }

  // 扫描通识必修课目录
  if (fs.existsSync(generalCoursesPath)) {
    structure["通识必修课"] = scanDirectory(generalCoursesPath);
  }

  return structure;
}

// 如果直接运行此脚本
if (require.main === module) {
  const structure = generateFileStructure();

  // 输出为JSON格式
  console.log(JSON.stringify(structure, null, 2));

  // 可选：保存到文件
  const outputPath = path.join(__dirname, "file-structure.json");
  fs.writeFileSync(outputPath, JSON.stringify(structure, null, 2), "utf8");
  console.log(`\nFile structure saved to: ${outputPath}`);
}

module.exports = { generateFileStructure, scanDirectory };
