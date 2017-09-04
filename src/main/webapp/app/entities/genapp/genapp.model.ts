import { BaseEntity } from './../../shared';

export class Genapp implements BaseEntity {
    constructor(
        public id?: number,
        public application_name?: string,
        public package_name?: string,
        public package_folder?: string,
        public server_port?: string,
        public database_type?: string,
        public application_prefix?: string,
    ) {
    }
}
