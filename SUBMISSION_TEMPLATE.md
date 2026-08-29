# SUBMISSION - Exit Exam MVC 1/2569 (เสาร์บ่าย)

## 1. วิธีเปิดโปรแกรม
* **ภาษา/เฟรมเวิร์ก:** Java
* **Entry point / คำสั่งเปิดโปรแกรม:** `javac Main.java` และ `java Main`
* **หมายเหตุที่จำเป็น:** ควรมีไฟล์ `gson-2.10.1.jar` สำหรับการดึงและอ่านข้อมูล JSON จากไฟล์ `Model/seed_data.json`

---

## 2. ตารางเชื่อมโยง Requirements

| Requirement | Model / Domain | Controller / Action | View / Screen |
| :---: | :--- | :--- | :--- |
| **R1** | `Repository`, `SeedData` | `AppController`, `Membersmanager`, `VoteController`, `ChangeRoleRequest` | `MainFrame` |
| **R2** | `Repository`, `SeedData` | `AppController`, `ChangeRoleRequest`, `Membersmanager` | `MainFrame` |
| **R3** | `Repository`, `SeedData` | `AppController`, `VoteController` | `MainFrame` |
| **R4** | `Repository`, `SeedData` | `AppController`, `Membersmanager`, `VoteController`, `ChangeRoleRequest` | `MainFrame` |
| **R5** | `Repository`, `SeedData` | `AppController` | `MainFrame` |

---

## 3. ผลการทดสอบ

| กรณี | ผ่าน/ไม่ผ่าน | หมายเหตุ (เฉพาะที่จำเป็น) |
| :---: | :---: | :--- |
| **T1** | ผ่าน | |
| **T2** | ผ่าน | |
| **T3** | ผ่าน | |
| **T4** | ผ่าน | |
| **T5** | ผ่าน | แต่ข้อมูลในตารางยังไม่ได้ถูกลบออกทันที |
| **T6** | ผ่าน | |

---

## 4. ความแตกต่างระหว่างแบบที่ออกแบบกับโปรแกรมจริง (ถ้ามี)
1. ในช่วงแรกวางแผนใช้ `AppController` จัดการทุกอย่าง แต่พบว่าควบคุมหลายส่วนเกินไป จึงแยก `VoteController` ออกมาเพื่อเพิ่มความชัดเจนและทำให้เข้าใจง่ายขึ้น
2. เนื่องจากเวลาค่อนข้างน้อย จึงใช้ Generative AI เข้ามาช่วยสร้างโค้ดในส่วน UI / Screen

---

## 5. บันทึกการใช้ Generative AI

| เวลาโดยประมาณ | เครื่องมือ | ใช้เพื่ออะไร | นำคำแนะนำไปใช้อย่างไร |
| :---: | :---: | :--- | :--- |
| **1:50 PM** | Gemini | หาวิธีอ่านไฟล์ JSON ใน Java | นำโค้ดคลาส `Repository` และแนวทางการใช้ `gson-2.10.1.jar` ไปปรับใช้ |
| **2:40 PM** | Gemini | หาวิธีสร้างตาราง (Table) ใน Java | นำโครงสร้างตารางใน `MainFrame` ไปใช้งาน |
| **2:50 PM** | Gemini | หาวิธีตั้งค่า Font ภาษาไทยใน Java Swing | นำการใช้ `FontUIResource` ไปตั้งค่าในหน้า `Main` |
| **3:10 PM** | Gemini | หาวิธีสร้าง Tab Bar ใน Java | นำ `JTabbedPane` ไปประยุกต์ใช้ใน `MainFrame` |
| **3:50 PM** | Gemini | หาวิธีเพิ่ม Dropdown Option | นำมาสร้าง `showRoleSelectionDialog` ใน `MainFrame` และ `showMemberSelectionDialog` ใน `AppController` |
| **4:00 PM** | Gemini | หาวิธีสร้าง Pop-up Error Dialog | นำเมธอด `showError` และ `showMessage` ไปใช้งาน |
