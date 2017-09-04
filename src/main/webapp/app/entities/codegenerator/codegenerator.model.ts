import { BaseEntity } from './../../shared';

export class Codegenerator implements BaseEntity {
    constructor(
        public id?: number,
        public application_name?: string,
        public package_name?: string,
    ) {
    }
}
