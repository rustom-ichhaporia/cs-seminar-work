rawtext = input("Please enter the string to be decrypted: ")

rawtext = rawtext.replace("(","")
rawtext = rawtext.replace(")", "")

ciphers = rawtext.split(", ")

for num in ciphers: 
    print(chr(int(num)+64), end='')

print("done")