export interface changed_value {
    changed_point: string;
    before_value: number;
    unit: string;
    changed_value: number;
}

export interface product {
    product_id: number;
    image_name: string;
    name: string;
    price: Number;
    brand: string;
    category: string;
    detail: productDetail[];
    uploader: string;
    uploaded_date: Date;
    updator: string;
    updated_date: Date;
    positive_point: number;
    negative_point: number;
}

export interface productDetail {
    product_detail_key: number;
    changed_point: string;
    before_detail: number;
    after_detail: number;
    unit: string;
}

export interface signupData {
    id: string;
    email: string;
    password: string;
    repeat_password: string;
    name: string;
    nickname: string;
    tel: string;
}

export interface loginData {
    id: string;
    password: string;
}