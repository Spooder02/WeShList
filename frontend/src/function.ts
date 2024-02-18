export default function appendOrReplaceFormData(formData: FormData, key: string, value: string) {
    if (formData.has(key)) formData.delete(key)
    formData.append(key, value);
}