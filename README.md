# Java Password Manager
This is a password manager application and IB documentation that I made for my IB Computer Science IA. It scored a 32/34 (7) from my teacher but was moderated by IB to be a 25/32, an IB 5 (yikes!!). Regardless, I hope you find this helpful if you are currently working on your IB CS IA (please do not copy directly). Also, if you are interested in other preparation resources I have prepared, including markschemes, check out this Google Drive folder: https://drive.google.com/drive/folders/1tOUggjBXtlpGN7V_oXN7soLkTNKjVDiH?usp=sharing

Features include:
- A master password is required to access the main menu; the user can create one if one
does not exist.
- There are limited login attempts with an incorrect master password.
- An account list, retrieved from an XML file and displayed in the main menu, is sorted in
descending order, with the most-used accounts at the top.
- Users can add, remove, and filter/search items from the account list.
- When adding an account, users can generate strong, randomized passwords that meet
standard password complexity requirements.
- Users can select a renewal period for a password when adding an account. The
program will display a message when a password is about to expire.
- The data is secure and encrypted with AES-256 using a password-based encryption
key.
- A password scan feature to identify any weak passwords that are being used.

## Usage

The rockyou.txt file is a placeholder due to file size limitations; you will need to download the original from https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt if you wish to have full functionality.

You can then create a new .jar from the files. Java 17 or later is required, else a runtime error will occur.

## Screenshots
![1](https://github.com/das150/Java-Password-Manager/assets/83658956/038a027e-3f72-4b4a-a8ab-cac625d06f06)
![2](https://github.com/das150/Java-Password-Manager/assets/83658956/b10b898d-3cb9-47c3-b7b3-8b420d648dd6)
![3](https://github.com/das150/Java-Password-Manager/assets/83658956/2444353b-9d3e-4860-bc2d-93885e57cd59)
![4](https://github.com/das150/Java-Password-Manager/assets/83658956/bb5eec31-3ff7-45db-8237-7ecf2b5eb129)
![5](https://github.com/das150/Java-Password-Manager/assets/83658956/7eb557b4-b4f7-44cf-ac1e-30b94f609737)
