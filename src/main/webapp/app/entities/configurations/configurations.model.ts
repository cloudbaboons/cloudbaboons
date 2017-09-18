import { BaseEntity } from './../../shared';

export class Configurations implements BaseEntity {
    constructor(
        public id?: number,
        public giturl?: string,
        public gitusername?: string,
        public gitpassword?: string,
        public jenkinsurl?: string,
        public jenkinsusername?: string,
        public jenkinspassword?: string,
        public appname?: string,
        public orgname?: string,
    ) {
    }
}
