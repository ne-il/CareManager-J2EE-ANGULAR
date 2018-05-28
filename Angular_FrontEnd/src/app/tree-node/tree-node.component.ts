import {Component, OnInit} from '@angular/core';
import {NodeRestEndpointApi, Node} from '../msnode/index'
import {TreeNode, SelectItem} from 'primeng/api';


@Component({
    selector: 'app-tree-node',
    templateUrl: './tree-node.component.html',
    styleUrls: ['./tree-node.component.css']
})


export class TreeNodeComponent implements OnInit {

    user;

    nodes = [];

    filesTree: TreeNode[];

    displayDialog: boolean;
    node: Node = null;
    selectedFile: TreeNode = {};

    types: SelectItem[] = [];

    constructor(private nodeService: NodeRestEndpointApi) {
    }

    ngOnInit() {

        this.user = localStorage.getItem('currentUser');

        this.nodeService.getNodes().subscribe(nodes => {
            for (let node of nodes) {
                if (node.fatherId == null) {
                    this.nodes.push(this.transform(node));
                }
            }

            this.filesTree = this.nodes;


        });
        this.types = [
            {label: 'Choisissez un type', value: null},
            {label: 'HOPITAL', value: 'ROOT'},
            {label: 'POLE', value: 'POLE'},
            {label: 'SERVICE', value: 'SERVICE'},
            {label: 'UNITE HOSPITALIERE', value: 'UNITE HOSPITALIERE'},
            {label: 'UNITE DE SOINS', value: 'UNITE DE SOINS'}
        ];

    }


    nodeSelect(event) {
        this.displayDialog = true;
        this.node = {};
        //event.node = selected node
    }

    save() {
        let name = this.node.name;
        let type = this.node.type;
        this.nodeService.getNodes().subscribe(nodes => {
            for (let node of nodes) {
                if (node.fatherId == null) {
                    this.nodes.push(this.transform(node));
                }
                if ((this.selectedFile.label == node.name) && (this.selectedFile.data.fatherId == node.fatherId)) {
                    this.node.fatherId = node.id;
                    let filesTree = [...this.filesTree];
                    this.node.childrens = [];
                    this.node.name = name;
                    this.node.type = type;
                    this.selectedFile.children.push(this.transform(this.node));
                    this.nodeService.createNode(this.node);
                    console.log(this.node);
                }
            }
        });
        this.node = {};
        this.displayDialog = false;

    }

    delete() {
        this.node = null;
        this.displayDialog = false;
    }

    private transform(node: Node): TreeNode {

        let treeNode: TreeNode = {
            label: node.name,
            data: node,
            children: []
        };

        node.childrens.forEach(worker => {
            treeNode.children.push(this.transform(worker));
        })

        return treeNode;

    }
}