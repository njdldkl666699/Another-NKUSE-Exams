import json
import pandas as pd

# 读取 JSON 文件
json_file_path = r"/体验智能体开发\output.json"
with open(json_file_path, "r", encoding="utf-8") as file:
    data = json.load(file)

# 提取 JSON 数组内容
output_data = data.get("output", [])

# 清理数据，去掉多余的换行符和大括号
cleaned_data = [item.strip("{}\n").strip() for item in output_data]

# 将数据转换为 DataFrame
df = pd.DataFrame(cleaned_data, columns=["描述"])

# 保存为 Excel 文件
excel_file_path = r"/体验智能体开发\output.xlsx"
df.to_excel(excel_file_path, index=False)

print(f"数据已成功保存到 {excel_file_path}")
