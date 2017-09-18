import { BaseEntity } from './../../shared';

export class Entities implements BaseEntity {
    constructor(
        public id?: number,
        public orgname?: string,
        public appname?: string,
        public fieldname?: string,
        public fieldtype?: string,
    ) {
    }
}
