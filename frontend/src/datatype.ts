export interface changed_value {
    changed_point: string;
    before_value: number;
    unit: string;
    changed_value: number;
}

export interface product {
    product_id: number;
    image_name: String;
    name: String;
    price: number;
    brand: String;
    category: String;
    detail: productDetail;
    uploader: String;
    uploaded_date: Date;
    updator: String;
    updated_date: Date;
    positive_point: number;
    negative_point: number;
}

export interface productDetail {
    product_detail_key: number;
    changed_point: String;
    before_detail: number;
    after_detail: number;
    unit: String;
}

export interface signupData {
    id: String;
    email: String;
    password: String;
    repeat_password: String;
    name: String;
    nickname: String;
    tel: String;
}

export interface loginData {
    id: String;
    password: String;
}