export default function appendOrReplaceFormData(formData: FormData, key: string, value: string) {
    if (formData.has(key)) formData.delete(key)
    formData.append(key, value);
}

export function changeDateFormat(targetDate: Date) {
    const date = new Date(targetDate);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');

    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    return formattedDate;
}

export function validUserCase(validData: string, a: () => void, b: () => void, c: () => void) {
    switch(validData) {
        case "Valid User":
            a();
            break;
        case "Expired User":
            b();
            break;
        case "Invalid User":
            c();
            break;
        default:
            alert("[에러] 로그인 정보가 없습니다. 다시 시도해주세요")
    }
}